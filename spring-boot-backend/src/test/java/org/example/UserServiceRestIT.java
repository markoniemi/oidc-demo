package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.Arrays;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.example.dto.UserDto;
import org.example.model.user.Role;
import org.example.security.JwtToken;
import org.example.service.user.UserRestClient;
import org.example.service.user.ValidationError;
import org.junit.jupiter.api.Test;

@Log4j2
public class UserServiceRestIT extends AbstractIntegrationTestBase {
  private final UserRestClient userService = new UserRestClient(JwtToken.create("admin"));

  @Test
  public void findAll()  {
    List<UserDto> users = userService.findAll();
    assertNotNull(users);
    assertEquals(6, users.size());
  }

  @Test
  public void find()  {
    List<UserDto> users = userService.findAll();
    assertNotNull(users);
    log.info(Arrays.toString(users.toArray()));
    assertEquals(6, users.size());
    users = userService.findByEmail("email0");
    assertNotNull(users);
    log.info(Arrays.toString(users.toArray()));
    assertEquals(1, users.size());
    assertEquals("email0", users.get(0).getEmail());
    users = userService.findByUsername("username0");
    assertNotNull(users);
    assertEquals(1, users.size());
    assertEquals("username0", users.get(0).getUsername());
  }

  @Test
  public void create()  {
    UserDto user = new UserDto(null, "username", "password", "email", Role.ROLE_USER);
    user = userService.create(user);
    assertNotNull(user);
    assertNotNull(user.getId());
    user = userService.find(user.getId());
    assertNotNull(user);
    assertNotNull(user.getId());
    userService.delete(user.getId());
  }

  @Test
  public void createWithInvalidUser()  {
    String userJson = "{\"username\":null}";
    List<ValidationError> validationErrors = userService.create(userJson, BAD_REQUEST);
    assertEquals(3, validationErrors.size());
    ValidationError validationError = validationErrors.get(0);
    log.debug(validationError);
    assertEquals("userDto", validationError.getObjectName());
//    assertEquals("password", validationError.getField());
    assertEquals("field.required", validationError.getDefaultMessage());
  }
  
  @Test
  public void createWithExistingUser() {
    UserDto user = new UserDto(null, "username", "password", "email", Role.ROLE_USER);
    user = userService.create(user);
    String userJson =
        "{\"username\":\"username\", \"password\":\"password\",\"email\":\"email\",\"role\":\"ROLE_USER\"}";
    userService.create(userJson, BAD_REQUEST);
    userService.delete(user.getId(), NO_CONTENT);
  }

  @Test
  public void updateWithInvalidUser()  {
    String userJson = "{\"id\":1, \"username\":null}";
    List<ValidationError> validationErrors = userService.update(userJson, 1, BAD_REQUEST);
    log.debug(Arrays.toString(validationErrors.toArray()));
    assertEquals(3, validationErrors.size());
    ValidationError validationError = validationErrors.get(0);
    assertEquals("userDto", validationError.getObjectName());
//    assertEquals("username", validationError.getField());
    assertEquals("field.required", validationError.getDefaultMessage());
  }
  @Test
  public void updateWithNonexistingUser()  {
    String userJson = "{\"id\":555, \"username\":\"username\"}";
    userService.update(userJson,555, BAD_REQUEST);
  }

  @Test
  public void deleteNonExistent() {
    userService.delete(1000L, NO_CONTENT);
  }
}
