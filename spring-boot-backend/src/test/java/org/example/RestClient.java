package org.example;

import static io.restassured.RestAssured.given;
import static org.springframework.http.HttpStatus.OK;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.Header;
import io.restassured.http.Headers;

public class RestClient {
  public static <T> T get(String url, TypeRef<T> typeRef, String token) {
    return get(url, typeRef, token, OK);
  }

  public static <T> T get(String url, TypeRef<T> typeRef, String token, HttpStatus httpStatus) {
    return given()
        .headers(createHeaders(token))
        .get(url)
        .then()
        .statusCode(httpStatus.value())
        .log()
        .ifError()
        .extract()
        .as(typeRef.getType());
  }

  public static <T> T get(String url, Class<T> clazz, String token) {
    return get(url, clazz, token, OK);
  }

  public static <T> T get(String url, Class<T> clazz, String token, HttpStatus httpStatus) {
    return given()
        .headers(createHeaders(token))
        .get(url)
        .then()
        .statusCode(httpStatus.value())
        .log()
        .ifError()
        .extract()
        .as(clazz);
  }

  public static <T> T post(String url, Object body, Class<T> clazz, String token) {
    return post(url, body, clazz, token, OK);
  }

  public static <T> T post(
      String url, Object body, Class<T> clazz, String token, HttpStatus httpStatus) {
    return given()
        .body(body)
        .headers(createHeaders(token))
        .post(url)
        .then()
        .statusCode(httpStatus.value())
        .log()
        .ifError()
        .extract()
        .as(clazz);
  }

  public static <T> T post(
      String url, String body, TypeRef<T> typeRef, String token, HttpStatus httpStatus) {
    return given()
        .body(body)
        .headers(createHeaders(token))
        .post(url)
        .then()
        .statusCode(httpStatus.value())
        .log()
        .ifError()
        .extract()
        .as(typeRef);
  }

  public static <T> T put(
      String url, String body, TypeRef<T> typeRef, String token, HttpStatus httpStatus) {
    return given()
        .body(body)
        .headers(createHeaders(token))
        .put(url)
        .then()
        .statusCode(httpStatus.value())
        .log()
        .ifError()
        .extract()
        .as(typeRef);
  }

  public static void delete(String url, String token, HttpStatus httpStatus) {
    given()
        .headers(createHeaders(token))
        .delete(url)
        .then()
        .statusCode(httpStatus.value())
        .log()
        .ifError();
  }

  private static Headers createHeaders(String token) {
    return new Headers(
        Arrays.asList(
            new Header("Authorization", "Bearer " + token),
            new Header("Content-Type", "application/json"),
            new Header("Accept", "application/json")));
  }
}
