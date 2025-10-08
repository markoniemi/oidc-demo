package org.example.rest;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

import org.example.service.user.ValidationError;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BindExceptionMapper implements ExceptionMapper<BindException> {
  ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public Response toResponse(BindException errors) {
    List<ValidationError> validationErrors =
        errors.getAllErrors().stream()
            .map(this::createValidationError)
            .collect(Collectors.toList());
    return Response.status(Response.Status.BAD_REQUEST)
        .entity(asJson(validationErrors))
        .type(MediaType.APPLICATION_JSON)
        .build();
  }

  private String asJson(List<ValidationError> validationErrors) {
    try {
      return objectMapper.writeValueAsString(validationErrors);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  private ValidationError createValidationError(ObjectError error) {
    ValidationError validationError =
        new ValidationError(
            error.getObjectName(),
            null,
            error.getCode(),
            error.getDefaultMessage(),
            error.getArguments());
    if (error instanceof FieldError) {
      validationError.setField(((FieldError) error).getField());
    }
    return validationError;
  }
}
