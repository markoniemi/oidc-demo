package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.client.ClientHttpRequestInterceptor;

import feign.RequestInterceptor;

@Import({SeleniumConfig.class})
public class IntegrationTestConfig {

  @Bean
  public RequestInterceptor jwtRequestInterceptor() {
    return new JwtRequestInterceptor();
  }

  @Bean
  public ClientHttpRequestInterceptor restRequestInterceptor() {
    return new RestRequestInterceptor();
  }
}
