package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.example.model.user.Role;
import org.example.model.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

public class LoginServiceRestTemplateIT extends AbstractIntegrationTestBase {
  private TestRestTemplate testRestTemplate = new TestRestTemplate();
  private String url = "http://localhost:8080";

  @Test
  public void login() throws JsonProcessingException {
    User user = new User("admin", "admin", "email", Role.ROLE_USER);
    String token =
        testRestTemplate.postForObject(
            url + "/api/rest/auth/login", new HttpEntity<User>(user), String.class);
    assertNotNull(token);
  }
}
