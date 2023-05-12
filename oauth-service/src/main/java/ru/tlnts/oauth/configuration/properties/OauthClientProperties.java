package ru.tlnts.oauth.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Set;

/**
 * @author mamedov
 */
@Getter
@Setter
@Validated
@Component
@ConfigurationProperties(prefix = "tlnts.oauth.client")
public class OauthClientProperties {

	@NotNull
	private Client web = new Client();

	@NotNull
	private Client oauth = new Client();


	@Getter
	@Setter
	@Validated
	public static class Client {

		private static final Duration DEFAULT_ACCESS_TOKEN_TTL = Duration.of(5, ChronoUnit.MINUTES);
		private static final Duration DEFAULT_REFRESH_TOKEN_TTL = Duration.of(1, ChronoUnit.DAYS);

		@NotNull
		private String id;

		@NotNull
		private String secret;

		@NotEmpty
		private Set<String> redirectUris;

		@NotEmpty
		private Set<String> scopes;

		@NotNull
		private Duration accessTokenTtl = DEFAULT_ACCESS_TOKEN_TTL;

		@NotNull
		private Duration refreshTokenTtl = DEFAULT_REFRESH_TOKEN_TTL;

	}
}
