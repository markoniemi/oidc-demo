package org.example.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.AbstractIntegrationTestBase;
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

  @BeforeEach
  void setUp() {
    userRepository.deleteAll();
token = getAccessToken("oidc-test", "Uq8odAqLX59MuZfNXRwgSRPA3w4qz5TW", "read write");
  }

  @Test
  void getAllUsers() throws Exception {
    User user = new User();
    user.setUsername("testuser");
    user.setEmail("testuser@example.com");
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
          "email": "newuser@example.com"
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
    user = userRepository.save(user);

    String userJson = """
        {
          "username": "updateduser"
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
    user = userRepository.save(user);

    mockMvc.perform(delete("/api/users/{id}", user.getId())
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
        .andExpect(status().isNoContent());
  }

  private String getAccessToken(String clientId, String clientSecret, String scope) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.setBasicAuth(clientId, clientSecret);

    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("grant_type", "client_credentials");
    map.add("scope", scope);

    HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

    ResponseEntity<TokenResponse> response = restTemplate.postForEntity(
        "http://localhost:9090/oauth2/token",
        entity,
        TokenResponse.class
    );

    return response.getBody().getAccessToken();
  }

  private static class TokenResponse {
    private String access_token;

    public String getAccessToken() {
      return access_token;
    }

    public void setAccessToken(String access_token) {
      this.access_token = access_token;
    }
  }
}
