package org.example.config;

import java.io.IOException;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

@NoArgsConstructor
public class RestRequestInterceptor implements ClientHttpRequestInterceptor {
  String jwtToken;

  public RestRequestInterceptor(String jwtToken) {
    this.jwtToken = jwtToken;
  }

  @Override
  public ClientHttpResponse intercept(
      HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
    request.getHeaders().add("Authorization", "Bearer " + jwtToken);
    request.getHeaders().add("Accept", MediaType.APPLICATION_JSON_VALUE);
    return execution.execute(request, body);
  }
}
