package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@Component
public class OAuthTokenHelper {
  @Value("${spring.security.oauth2.client.provider.keycloak.token-uri}")
  private String tokenUri;

  @Autowired OAuthConfig config;

  public String getAccessToken() {
    Response response =
        RestAssured.given()
            .baseUri(tokenUri)
            .header("Content-Type", "application/x-www-form-urlencoded")
            .formParam("client_id", config.getClientId())
            .formParam("client_secret", config.getClientSecret())
            .formParam("grant_type", config.getAuthorizationGrantType())
            .post()
            .then()
            .statusCode(200)
            .log()
            .ifError()
            .and()
            .extract()
            .response();
    return response.jsonPath().getString("access_token");
  }
}
