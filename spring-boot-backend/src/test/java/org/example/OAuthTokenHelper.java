package org.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OAuthTokenHelper {
  private String tokenUri;
  private String clientId;
  private String clientSecret;
  private String authorizationGrantType;

  public OAuthTokenHelper(
      @Value("${spring.oauth.token-uri}") String tokenUri,
      @Value("${spring.oauth.client-id}") String clientId,
      @Value("${spring.oauth.client-secret}") String clientSecret,
      @Value("${spring.oauth.authorization-grant-type}") String authorizationGrantType) {
    this.tokenUri = tokenUri;
    this.clientId = clientId;
    this.clientSecret = clientSecret;
    this.authorizationGrantType = authorizationGrantType;
  }

  public String getAccessToken() {
    Response response =
        RestAssured.given()
            .baseUri(tokenUri)
            .header("Content-Type", "application/x-www-form-urlencoded")
            .formParam("client_id", clientId)
            .formParam("client_secret", clientSecret)
            .formParam("grant_type", authorizationGrantType)
            .formParam("authentication_method", "client_secret_post")
            .post()
            .then()
            .extract()
            .response();
    return response.jsonPath().getString("access_token");
  }
}
