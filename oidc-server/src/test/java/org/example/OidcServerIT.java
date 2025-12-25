package org.example;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(classes = AuthorizationServerApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
class OidcServerIT {
  @Autowired OAuthTokenHelper oAuthTokenHelper;

  @Test
  public void getAccessToken() {
    assertNotNull(oAuthTokenHelper.getAccessToken());
  }
}
