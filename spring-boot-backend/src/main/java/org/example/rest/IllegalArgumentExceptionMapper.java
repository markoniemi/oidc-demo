package org.example.rest;

import java.util.ArrayList;
import java.util.List;
import org.example.service.user.ValidationError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {
  ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public Response toResponse(IllegalArgumentException exception) {
    ValidationError validationError = createValidationError(exception);
    List<ValidationError> validationErrors = new ArrayList<>();
    validationErrors.add(validationError);
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

  private ValidationError createValidationError(IllegalArgumentException exception) {
    return new ValidationError(null, null, exception.getMessage(), null, null);
  }
}
