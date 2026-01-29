package org.example;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// We run the test on a fixed port that matches the configuration in application.properties
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
    properties = "server.port=9090")
public class OidcServerIT {

  @Autowired private OAuthTokenHelper oauthTokenHelper;
  @Test
  public void getAccessToken() {
    assertNotNull(oauthTokenHelper.getAccessToken());
  }


  @Test
  void getAccessTokenUsingPkceFlow() {
    assertNotNull(oauthTokenHelper.getAccessTokenWithPkce());
  }
}