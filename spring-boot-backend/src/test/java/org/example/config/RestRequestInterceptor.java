package org.example.config;

import java.io.IOException;

import org.example.security.JwtToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class RestRequestInterceptor implements ClientHttpRequestInterceptor {
  // TODO constructor inject the token
  @Value("jwt")
  private String jwtToken;

  @Override
  public ClientHttpResponse intercept(
      HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
    request.getHeaders().add("Authorization", "Bearer " + JwtToken.createToken("admin1"));
    request.getHeaders().add("Accept", MediaType.APPLICATION_JSON_VALUE);
    //        request.getHeaders().add("Content type", MediaType.APPLICATION_JSON_VALUE);
    return execution.execute(request, body);
  }
}
