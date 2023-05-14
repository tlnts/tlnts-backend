package ru.tlnts.oauth.configuration;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.DelegatingOAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.JwtGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2AccessTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2RefreshTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import ru.tlnts.oauth.configuration.properties.OauthClientProperties;
import ru.tlnts.oauth.storage.user.dao.UserDao;
import ru.tlnts.oauth.storage.user.security.UserDetailsImpl;
import ru.tlnts.oauth.storage.user.security.UserDetailsServiceImpl;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author mamedov
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableAutoConfiguration(exclude = ErrorMvcAutoConfiguration.class)
public class OauthServerConfig {

	private final OauthClientProperties clientProperties;

	@Bean
	@Order(1)
	public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
		// Enable authorization server autoconfiguration
		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

		return http
				.csrf().disable()
				.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
				// Enable OpenID Connect 1.0
				.oidc(Customizer.withDefaults())
				// Token generator
				.tokenGenerator(tokenGenerator()).and()
				// Redirect to the login page when not authenticated from the authorization endpoint
				.exceptionHandling((exceptions) -> exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")))
				// Accept access tokens for User Info and/or Client Registration
				.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
				.build();
	}

	@Bean
	@Order(2)
	public SecurityFilterChain oauthApiSecurityFilterChain(HttpSecurity http) throws Exception {
		String oauthScope = clientProperties.getOauth().getScopes().iterator().next();

		return http
				.csrf().disable()
				.authorizeHttpRequests(authorize -> authorize
						.antMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
						.antMatchers("/api/user/**").hasAuthority("SCOPE_" + oauthScope)
						.anyRequest().denyAll())
				.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
				.formLogin(Customizer.withDefaults())
				.build();
	}

	@Bean
	public OAuth2AuthorizationService oAuth2AuthorizationService(JdbcTemplate jdbcTemplate) {
		return new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository());
	}

	@Bean
	public UserDetailsService userDetailsService(UserDao userDao) {
		return new UserDetailsServiceImpl(userDao);
	}

	@Bean
	public RegisteredClientRepository registeredClientRepository() {
		OauthClientProperties.Client oauth = clientProperties.getOauth();
		RegisteredClient adminClient = RegisteredClient.withId(oauth.getId())
				.clientId(oauth.getId())
				.clientSecret("{noop}" + oauth.getSecret())
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				.redirectUris(uris -> uris.addAll(oauth.getRedirectUris()))
				.scopes(scopes -> scopes.addAll(oauth.getScopes()))
				.scope(OidcScopes.OPENID)
				.scope(OidcScopes.PROFILE)
				.tokenSettings(TokenSettings.builder()
						.accessTokenTimeToLive(oauth.getAccessTokenTtl())
						.build())
				.clientSettings(ClientSettings.builder()
						.requireProofKey(false)
						.requireAuthorizationConsent(false)
						.build())
				.build();

		OauthClientProperties.Client web = clientProperties.getWeb();
		RegisteredClient webClient = RegisteredClient.withId(web.getId())
				.clientId(web.getId())
				.clientSecret("{noop}" + web.getSecret())
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				.redirectUris(uris -> uris.addAll(web.getRedirectUris()))
				.scopes(scopes -> scopes.addAll(web.getScopes()))
				.scope(OidcScopes.OPENID)
				.scope(OidcScopes.PROFILE)
				.tokenSettings(TokenSettings.builder()
						.accessTokenTimeToLive(web.getAccessTokenTtl())
						.refreshTokenTimeToLive(web.getRefreshTokenTtl())
						.reuseRefreshTokens(false)
						.build())
				.clientSettings(ClientSettings.builder()
						.requireProofKey(false)
						.requireAuthorizationConsent(false)
						.build())
				.build();

		// In-memory because only two clients now
		return new InMemoryRegisteredClientRepository(adminClient, webClient);
	}

	@Bean
	public AuthorizationServerSettings authorizationServerSettings() {
		return AuthorizationServerSettings.builder().build();
	}

	@Bean
	public OAuth2TokenGenerator<?> tokenGenerator() {
		JwtEncoder jwtEncoder = new NimbusJwtEncoder(jwkSource());
		JwtGenerator jwtGenerator = new JwtGenerator(jwtEncoder);
		jwtGenerator.setJwtCustomizer(jwtCustomizer());
		OAuth2AccessTokenGenerator accessTokenGenerator = new OAuth2AccessTokenGenerator();
		OAuth2RefreshTokenGenerator refreshTokenGenerator = new OAuth2RefreshTokenGenerator();
		return new DelegatingOAuth2TokenGenerator(jwtGenerator, accessTokenGenerator, refreshTokenGenerator);
	}

	@Bean
	public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
		return context -> context.getClaims().claims(claims -> {
			if (context.getTokenType().equals(OAuth2TokenType.ACCESS_TOKEN)
					&& context.getAuthorizationGrantType().equals(AuthorizationGrantType.CLIENT_CREDENTIALS)
					&& !claims.containsKey("scope")) {
				if (context.getRegisteredClient() != null) {
					claims.put("scope", context.getRegisteredClient().getScopes());
				} else if (context.getAuthorization() != null) {
					claims.put("scope", context.getAuthorization().getAuthorizedScopes());
				}
			}

			if (context.getTokenType().equals(OAuth2TokenType.ACCESS_TOKEN)
					&& context.getPrincipal() != null
					&& context.getPrincipal().getPrincipal() != null
					&& context.getPrincipal().getPrincipal() instanceof UserDetailsImpl userDetails) {
				claims.put("role", userDetails.getAuthorities()
						.stream()
						.map(GrantedAuthority::getAuthority)
						// Mutable because immutable not serialized when token saved with JdbcOAuth2AuthorizationService
						.collect(Collectors.toList()));

				if (!claims.containsKey("scope")) {
					claims.put("scope", context.getRegisteredClient().getScopes());
				} else if (context.getAuthorization() != null) {
					claims.put("scope", context.getAuthorization().getAuthorizedScopes());
				}
			}
		});
	}

	@Bean
	public JWKSource<SecurityContext> jwkSource() {
		KeyPair keyPair = generateRsaKey();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		RSAKey rsaKey = new RSAKey.Builder(publicKey)
				.privateKey(privateKey)
				.keyID(UUID.randomUUID().toString())
				.build();
		JWKSet jwkSet = new JWKSet(rsaKey);
		return new ImmutableJWKSet<>(jwkSet);
	}

	@Bean
	public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
		return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		String idForEncode = "bcrypt";

		Map<String, PasswordEncoder> encoders = new HashMap<>();
		encoders.put(idForEncode, new BCryptPasswordEncoder(12));
		encoders.put("noop", NoOpPasswordEncoder.getInstance());

		return new DelegatingPasswordEncoder(idForEncode, encoders);
	}


	private static KeyPair generateRsaKey() {
		KeyPair keyPair;
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048);
			keyPair = keyPairGenerator.generateKeyPair();
		} catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
		return keyPair;
	}

}
