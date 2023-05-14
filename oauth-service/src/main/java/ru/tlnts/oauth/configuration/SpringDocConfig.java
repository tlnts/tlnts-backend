package ru.tlnts.oauth.configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SecurityScheme(name = "oauth2_client_credentials_flow",
		type = SecuritySchemeType.OAUTH2,
		flows = @OAuthFlows(clientCredentials = @OAuthFlow(tokenUrl = "/oauth2/token")))
@Configuration
public class SpringDocConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("OAuth2 API")
						.contact(new Contact()
								.name("Mamedov Dmitry")
								.email("dmitrymamedov1996@gmai.com")
								.url("https://github.com/kiborroq")));
	}

}
