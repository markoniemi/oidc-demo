package org.example.service.user;

import static org.example.RestClient.get;
import static org.example.RestClient.post;
import static org.example.RestClient.put;
import static org.example.security.JwtToken.createToken;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import org.example.RestClient;
import org.example.model.user.User;
import org.springframework.http.HttpStatus;

import io.restassured.common.mapper.TypeRef;

public class UserRestClient {
  private String url = "http://localhost:8080/api/rest";

  public List<User> findAll() {
    return get(url + "/users/", userListType(), createToken("admin"));
  }

  public List<User> findByEmail(String email) {
    return get(url + "/users?email=" + email, userListType(), createToken("admin"));
  }

  public List<User> findByUsername(String username) {
    return get(url + "/users?username=" + username, userListType(), createToken("admin"));
  }

  public User create(User user) {
    return post(url + "/users", user, User.class, createToken("admin"));
  }

  public List<ValidationError> create(String userJson, HttpStatus httpStatus) {
    return post(url + "/users", userJson, errorListType(), createToken("admin"), httpStatus);
  }

  public List<ValidationError> update(String userJson, long id, HttpStatus httpStatus) {
    return put(url + "/users/" + id, userJson, errorListType(), createToken("admin"), httpStatus);
  }

  public User find(Long id) {
    return get(url + "/users/" + id, User.class, createToken("admin"));
  }

  public void delete(Long id) {
    delete(id, NO_CONTENT);
  }

  public void delete(Long id, HttpStatus httpStatus) {
    RestClient.delete(url + "/users/" + id, createToken("admin"), httpStatus);
  }

  private TypeRef<List<User>> userListType() {
    return new TypeRef<List<User>>() {};
  }

  private TypeRef<List<ValidationError>> errorListType() {
    return new TypeRef<List<ValidationError>>() {};
  }
}
