package org.example.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.AbstractIntegrationTestBase;
import org.example.OAuthTokenHelper;
import org.example.model.user.Role;
import org.example.model.user.User;
import org.example.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@AutoConfigureMockMvc
public class UserControllerIT extends AbstractIntegrationTestBase {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ObjectMapper objectMapper;
  private String token;
  @Autowired
  private OAuthTokenHelper oAuthTokenHelper;

  @BeforeEach
  void setUp() {
    userRepository.deleteAll();
token = oAuthTokenHelper.getAccessToken();
  }

  @Test
  void getAllUsers() throws Exception {
    User user = new User();
    user.setUsername("testuser");
    user.setEmail("testuser@example.com");
    user.setPassword("password");
    userRepository.save(user);

    mockMvc.perform(get("/api/users")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].username").value("testuser"));
  }

  @Test
  void getUserById() throws Exception {
    User user = new User();
    user.setUsername("testuser");
    user.setEmail("testuser@example.com");
    user.setPassword("password");
    user.setRole(Role.ROLE_ADMIN);
    user = userRepository.save(user);

    mockMvc.perform(get("/api/users/{id}", user.getId())
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value("testuser"));
  }

  @Test
  void createUser() throws Exception {
    String userJson = """
        {
          "username": "newuser",
          "email": "newuser@example.com",
          "password": "password",
          "role": "ROLE_ADMIN"
        }
        """;

    mockMvc.perform(post("/api/users")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(userJson))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.username").value("newuser"));
  }

  @Test
  void updateUser() throws Exception {
    User user = new User();
    user.setUsername("testuser");
    user.setEmail("testuser@example.com");
    user.setPassword("password");
    user.setRole(Role.ROLE_ADMIN);
    user = userRepository.save(user);

    String userJson = """
        {
          "username": "updateduser",
          "email": "newuser@example.com",
          "password": "password",
          "role": "ROLE_ADMIN"
        }
        """;

    mockMvc.perform(put("/api/users/{id}", user.getId())
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(userJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value("updateduser"));
  }

  @Test
  void deleteUser() throws Exception {
    User user = new User();
    user.setUsername("testuser");
    user.setEmail("testuser@example.com");
    user.setPassword("password");
    user.setRole(Role.ROLE_ADMIN);
    user = userRepository.save(user);

    mockMvc.perform(delete("/api/users/{id}", user.getId())
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
        .andExpect(status().isNoContent());
  }
}
