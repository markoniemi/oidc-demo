package org.example.config;

import org.example.security.JwtToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import feign.RequestInterceptor;

@Configuration
@Import({SeleniumConfig.class})
@PropertySource("datasource-hsqldb-it.properties")
@Profile("hsqldb")
public class HsqldbIntegrationTestConfig {

  @Bean
  public RequestInterceptor jwtRequestInterceptor() {
    return new JwtRequestInterceptor(JwtToken.create("admin"));
  }
}
