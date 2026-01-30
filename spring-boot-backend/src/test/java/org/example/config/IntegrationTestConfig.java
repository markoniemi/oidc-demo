package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.client.ClientHttpRequestInterceptor;

@Import({SeleniumConfig.class})
public class IntegrationTestConfig {

  @Bean
  public ClientHttpRequestInterceptor restRequestInterceptor() {
    return new RestRequestInterceptor();
  }
}
