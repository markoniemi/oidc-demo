package org.example.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class JwtRequestInterceptor implements RequestInterceptor {
  String jwtToken;

  JwtRequestInterceptor(String jwtToken) {
    this.jwtToken = jwtToken;
  }

  @Override
  public void apply(RequestTemplate requestTemplate) {
    requestTemplate.header("Authorization", "Bearer " + jwtToken);
  }
}
