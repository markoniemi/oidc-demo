package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OAuthTokenHelper {
  private final String tokenUri;
  private final String clientId;
  private final String clientSecret;
  private final String authorizationUri;
  private final String redirectUri;
  private final String scope;
  private final String testUsername;
  private final String testPassword;

  public OAuthTokenHelper(
      @Value("${spring.oauth.token-uri}") String tokenUri,
      @Value("${spring.oauth.client-id}") String clientId,
      @Value("${spring.oauth.client-secret}") String clientSecret,
      @Value("${spring.oauth.authorization-uri}") String authorizationUri,
      @Value("${spring.oauth.redirect-uri}") String redirectUri,
      @Value("${spring.oauth.scope}") String scope,
      @Value("${spring.oauth.test.username}") String testUsername,
      @Value("${spring.oauth.test.password}") String testPassword) {
    this.tokenUri = tokenUri;
    this.clientId = clientId;
    this.clientSecret = clientSecret;
    this.authorizationUri = authorizationUri;
    this.redirectUri = redirectUri;
    this.scope = scope;
    this.testUsername = testUsername;
    this.testPassword = testPassword;
  }

  public String getAccessToken() {
    Response response =
        RestAssured.given()
            .baseUri(tokenUri)
            .header("Content-Type", "application/x-www-form-urlencoded")
            .formParam("client_id", clientId)
            .formParam("client_secret", clientSecret)
            .formParam("grant_type", "client_credentials")
            .formParam("authentication_method", "client_secret_post")
            .post()
            .then()
            .extract()
            .response();
    return response.jsonPath().getString("access_token");
  }

  public String getAccessTokenWithPkce() {
    String codeVerifier = generateCodeVerifier();
    String codeChallenge = generateCodeChallenge(codeVerifier);

    // 1. Authenticate to get a session cookie
    Response loginResponse =
        RestAssured.given()
            .baseUri(tokenUri.substring(0, tokenUri.indexOf("/oauth2/token")))
            .redirects()
            .follow(false)
            .formParam("username", testUsername)
            .formParam("password", testPassword)
            .post("/login");

    String sessionCookie = loginResponse.getCookie("JSESSIONID");
    if (sessionCookie == null) {
      log.error("Authentication failed. Could not get session cookie. Status: {}, Body: {}",
          loginResponse.getStatusCode(), loginResponse.getBody().asString());
      throw new IllegalStateException("Authentication failed for test user.");
    }

    // 2. Get Authorization Code using the session
    Response authResponse =
        RestAssured.given()
            .cookie("JSESSIONID", sessionCookie)
            .baseUri(authorizationUri)
            .queryParam("response_type", "code")
            .queryParam("client_id", clientId)
            .queryParam("redirect_uri", redirectUri)
            .queryParam("scope", scope)
            .queryParam("code_challenge", codeChallenge)
            .queryParam("code_challenge_method", "S256")
            .redirects()
            .follow(false) // Do not follow redirects automatically
            .get();

    // Extract the authorization code from the redirect URI
    String location = authResponse.getHeader("Location");
    if (location == null) {
      log.error(
          "Failed to get authorization code. The 'Location' header is missing from the response. "
              + "This might mean the authorization server returned an error or a login page instead of a redirect. "
              + "Auth server response status: {}, Response body: {}",
          authResponse.getStatusCode(),
          authResponse.getBody().asString());
      throw new IllegalStateException(
          "Could not retrieve authorization code from the authorization server.");
    }

    // A more robust way to extract the 'code' parameter from the redirect URI
    String authorizationCode =
        Arrays.stream(location.substring(location.indexOf('?') + 1).split("&"))
            .filter(param -> param.startsWith("code="))
            .findFirst()
            .map(param -> param.substring(5))
            .orElseThrow(
                () -> new IllegalStateException("Authorization code not found in redirect URI: " + location));
    // 3. Exchange Authorization Code for Access Token
    Response tokenResponse =
        RestAssured.given()
            .baseUri(tokenUri)
            .header("Content-Type", "application/x-www-form-urlencoded")
            .formParam("client_id", clientId)
            .formParam("redirect_uri", redirectUri)
            .formParam("grant_type", "authorization_code")
            .formParam("code", authorizationCode)
            .formParam("code_verifier", codeVerifier)
            .post()
            .then()
            .extract()
            .response();

    return tokenResponse.jsonPath().getString("access_token");
  }

  private String generateCodeVerifier() {
    SecureRandom sr = new SecureRandom();
    byte[] codeVerifier = new byte[32];
    sr.nextBytes(codeVerifier);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(codeVerifier);
  }

  private String generateCodeChallenge(String codeVerifier) {
    try {
      byte[] bytes = codeVerifier.getBytes(StandardCharsets.US_ASCII);
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update(bytes, 0, bytes.length);
      byte[] digest = md.digest();
      return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
    } catch (NoSuchAlgorithmException e) {
      log.error("Failed to generate code challenge", e);
      throw new RuntimeException(e);
    }
  }
}
