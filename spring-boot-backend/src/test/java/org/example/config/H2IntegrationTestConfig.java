package org.example.config;

import java.net.URISyntaxException;

import org.example.security.JwtToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import feign.RequestInterceptor;

@Configuration
@Import({SeleniumConfig.class})
@PropertySource("datasource-h2-it.properties")
@Profile("h2")
public class H2IntegrationTestConfig {
  @Bean
  public RequestInterceptor jwtRequestInterceptor() throws URISyntaxException {
	    return new JwtRequestInterceptor(JwtToken.create("admin"));
  }
}
