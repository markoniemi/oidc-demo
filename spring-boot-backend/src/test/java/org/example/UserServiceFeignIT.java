package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.io.IOException;
import java.util.List;
import org.example.model.user.Role;
import org.example.model.user.User;
import org.example.service.user.UserService;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceFeignIT extends AbstractIntegrationTestBase {
  private UserService userService;

  @Autowired
  public UserServiceFeignIT(UserService userService) {
    this.userService = userService;
  }

  @Test
  public void findAll() throws JsonParseException, JsonMappingException, IOException {
    List<User> users = userService.findAll();
    assertNotNull(users);
    assertEquals(6, users.size());
  }

  @Test
  @DatabaseSetup(value = "classpath:users.xml", type = DatabaseOperation.REFRESH)
  @DatabaseTearDown(value = "classpath:users.xml", type = DatabaseOperation.DELETE)
  public void create() {
    User user = new User("username", "password", "email", Role.ROLE_USER);
    userService.create(user);
    User savedUser = userService.findByUsername("username");
    assertEquals("username", savedUser.getUsername());
    assertEquals("email", savedUser.getEmail());
    savedUser = userService.findById(savedUser.getId());
    assertEquals("username", user.getUsername());
    userService.delete(savedUser.getId());
    savedUser = userService.findByUsername("username");
    assertNull(savedUser);
  }
}
