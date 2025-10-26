package org.example;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.keycloak")
public class OAuthConfig {
	private String tokenUri;
	private String clientId;
	private String clientSecret;
	private String authorizationGrantType;
}
