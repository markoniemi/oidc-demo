package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.Arrays;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.example.model.user.Role;
import org.example.model.user.User;
import org.example.service.user.UserRestClient;
import org.example.service.user.ValidationError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
public class UserServiceOAuthRestIT extends AbstractIntegrationTestBase {
  private UserRestClient userRestClient;
  private final OAuthTokenHelper oAuthTokenHelper;

  @Autowired
  public UserServiceOAuthRestIT( OAuthTokenHelper oAuthTokenHelper) {
    this.oAuthTokenHelper = oAuthTokenHelper;
  }

  @BeforeEach
  public void init() {
    userRestClient = new UserRestClient(oAuthTokenHelper.getAccessToken());
  }

  @Test
  public void findAll()  {
    List<User> users = userRestClient.findAll();
    assertNotNull(users);
    assertEquals(6, users.size());
  }

  @Test
  public void find()  {
    List<User> users = userRestClient.findAll();
    assertNotNull(users);
    log.info(Arrays.toString(users.toArray()));
    assertEquals(6, users.size());
    users = userRestClient.findByEmail("email0");
    assertNotNull(users);
    log.info(Arrays.toString(users.toArray()));
    assertEquals(1, users.size());
    assertEquals("email0", users.get(0).getEmail());
    users = userRestClient.findByUsername("username0");
    assertNotNull(users);
    assertEquals(1, users.size());
    assertEquals("username0", users.get(0).getUsername());
  }

  @Test
  public void create()  {
    User user = new User("username", "password", "email", Role.ROLE_USER);
    user = userRestClient.create(user);
    assertNotNull(user);
    assertNotNull(user.getId());
    user = userRestClient.find(user.getId());
    assertNotNull(user);
    assertNotNull(user.getId());
    userRestClient.delete(user.getId());
  }

  @Test
  public void createWithInvalidUser()  {
    String userJson = "{\"username\":null}";
    List<ValidationError> validationErrors = userRestClient.create(userJson, BAD_REQUEST);
    assertEquals(3, validationErrors.size());
    ValidationError validationError = validationErrors.get(0);
    log.debug(validationError);
    assertEquals("User", validationError.getObjectName());
    //    assertEquals("password", validationError.getField());
    assertEquals("field.required", validationError.getCode());
  }

  @Test
  public void createWithExistingUser() {
    User user = new User("username", "password", "email", Role.ROLE_USER);
    user = userRestClient.create(user);
    String userJson = "{\"username\":\"username\"}";
    userRestClient.create(userJson, BAD_REQUEST);
    userRestClient.delete(user.getId(), NO_CONTENT);
  }

  @Test
  public void updateWithInvalidUser()  {
    String userJson = "{\"id\":1, \"username\":null}";
    List<ValidationError> validationErrors = userRestClient.update(userJson, 1, BAD_REQUEST);
    log.debug(Arrays.toString(validationErrors.toArray()));
    assertEquals(3, validationErrors.size());
    ValidationError validationError = validationErrors.get(0);
    assertEquals("User", validationError.getObjectName());
    //    assertEquals("password", validationError.getField());
    assertEquals("field.required", validationError.getCode());
  }

  @Test
  public void updateWithNonexistingUser()  {
    String userJson = "{\"id\":555, \"username\":\"username\"}";
    userRestClient.update(userJson, 555, BAD_REQUEST);
  }

  @Test
  public void deleteNonExistent() {
    userRestClient.delete(1000L, NO_CONTENT);
  }
}
