package org.example.service.user;

import static org.example.RestClient.get;
import static org.example.RestClient.post;
import static org.example.RestClient.put;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import io.restassured.common.mapper.TypeRef;
import java.util.List;
import org.example.RestClient;
import org.example.dto.UserDto;
import org.springframework.http.HttpStatus;

public class UserRestClient {
  private final String url = "http://localhost:8080/api/rest";
  private final String token;

  public UserRestClient(String token) {
    this.token = token;
  }

  public List<UserDto> findAll() {
    return get(url + "/users/", userDtoListType(), token);
  }

  public List<UserDto> findByEmail(String email) {
    return get(url + "/users?email=" + email, userDtoListType(), token);
  }

  public List<UserDto> findByUsername(String username) {
    return get(url + "/users?username=" + username, userDtoListType(), token);
  }

  public UserDto create(UserDto user) {
    return post(url + "/users", user, UserDto.class, token);
  }

  public List<ValidationError> create(String userJson, HttpStatus httpStatus) {
    return post(url + "/users", userJson, errorListType(), token, httpStatus);
  }

  public List<ValidationError> update(String userJson, long id, HttpStatus httpStatus) {
    return put(url + "/users/" + id, userJson, errorListType(), token, httpStatus);
  }

  public UserDto find(Long id) {
    return get(url + "/users/" + id, UserDto.class, token);
  }

  public void delete(Long id) {
    delete(id, NO_CONTENT);
  }

  public void delete(Long id, HttpStatus httpStatus) {
    RestClient.delete(url + "/users/" + id, token, httpStatus);
  }

  private TypeRef<List<UserDto>> userDtoListType() {
    return new TypeRef<>() {};
  }

  private TypeRef<List<ValidationError>> errorListType() {
    return new TypeRef<>() {};
  }
}
