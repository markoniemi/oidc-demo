package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.Collections;

import org.example.config.RestRequestInterceptor;
import org.example.model.user.Role;
import org.example.model.user.User;
import org.example.security.JwtToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class UserServiceRestTemplateIT extends AbstractIntegrationTestBase {
  private TestRestTemplate testRestTemplate = new TestRestTemplate();
  private String url = "http://localhost:8080";

  @BeforeEach
  public void setUp() {
	  RestRequestInterceptor requestInterceptor=new RestRequestInterceptor(JwtToken.create("admin"));
    testRestTemplate
        .getRestTemplate()
        .setInterceptors(Collections.singletonList(requestInterceptor));
  }

  @Test
  public void findAll() throws JsonParseException, JsonMappingException, IOException {
    User[] users = testRestTemplate.getForObject(url + "/api/rest/users", User[].class);
    assertNotNull(users);
    assertEquals(6, users.length);
  }

  @Test
  public void findById() throws JsonParseException, JsonMappingException, IOException {
    User user = testRestTemplate.getForObject(url + "/api/rest/users/1", User.class);
    assertNotNull(user);
  }

  @Test
  public void findByUsername() throws JsonParseException, JsonMappingException, IOException {
    User user = testRestTemplate.getForObject(url + "/api/rest/users/username/admin", User.class);
    assertEquals("admin", user.getUsername());
  }

  @Test
  public void create() throws JsonProcessingException {
    User user = new User("test", "test", "email", Role.ROLE_USER);
    User savedUser =
        testRestTemplate.postForObject(
            url + "/api/rest/users", new HttpEntity<User>(user), User.class);
    assertNotNull(savedUser);
    assertNotNull(savedUser.getId());
    User[] users = testRestTemplate.getForObject(url + "/api/rest/users", User[].class);
    assertEquals(7, users.length);
    user = testRestTemplate.getForObject(url + "/api/rest/users/username/test", User.class);
    assertEquals("test", user.getUsername());
    testRestTemplate.delete(url + "/api/rest/users/" + user.getId());
    user = testRestTemplate.getForObject(url + "/api/rest/users/username/test", User.class);
  }

  @Test
  public void deleteNonExistent() {
    try {
      testRestTemplate.delete(url + "/api/rest/users/10000");
    } catch (HttpClientErrorException e) {
      log.error(e);
    } catch (Throwable e) {
      log.error(e);
    }
  }
}
