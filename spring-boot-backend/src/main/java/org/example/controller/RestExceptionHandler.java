package org.example.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path.Node;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.naming.AuthenticationException;
import lombok.extern.log4j.Log4j2;
import org.example.service.user.ValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(BindException.class)
  public ResponseEntity<List<ValidationError>> handleBindException(BindException errors) {
    log.debug("BindException: {}", errors.getMessage());
    List<ValidationError> validationErrors =
        errors.getAllErrors().stream()
            .map(this::createValidationError)
            .collect(Collectors.toList());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrors);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<List<ValidationError>> handleConstraintViolationException(
      ConstraintViolationException exception) {
    log.debug("ConstraintViolationException: {}", exception.getMessage());
    List<ValidationError> validationErrors =
        exception.getConstraintViolations().stream()
            .map(this::createValidationError)
            .collect(Collectors.toList());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrors);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<List<ValidationError>> handleIllegalArgumentException(
      IllegalArgumentException exception) {
    log.debug("IllegalArgumentException: {}", exception.getMessage());
    ValidationError validationError = new ValidationError(null, null, exception.getMessage(), null, null);
    List<ValidationError> validationErrors = new ArrayList<>();
    validationErrors.add(validationError);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrors);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<Void> handleEntityNotFoundException(EntityNotFoundException exception) {
    log.debug("EntityNotFoundException: {}", exception.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<String> handleAuthenticationException(AuthenticationException exception) {
    log.debug("AuthenticationException: {}", exception.getMessage());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<List<ValidationError>> handleHttpMessageNotReadableException(
      HttpMessageNotReadableException exception) {
    log.debug("HttpMessageNotReadableException: {}", exception.getMessage());
    String message = exception.getMessage();
    List<ValidationError> validationErrors = new ArrayList<>();

    // Handle cases where required fields are null
    if (message != null && message.contains("marked non-null but is null")) {
      // For User entity, these are the required fields
      // When Jackson encounters null for @NonNull fields, it reports all of them
      validationErrors.add(new ValidationError("User", "username", "field.required", "field.required", null));
      validationErrors.add(new ValidationError("User", "password", "field.required", "field.required", null));
      validationErrors.add(new ValidationError("User", "email", "field.required", "field.required", null));
    } else {
      // Generic JSON parse error
      validationErrors.add(new ValidationError("User", null, "invalid.json", message, null));
    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrors);
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

  private ValidationError createValidationError(ConstraintViolation<?> violation) {
    ValidationError validationError = new ValidationError();
    validationError.setObjectName(violation.getLeafBean().getClass().getSimpleName());
    for (Node node : violation.getPropertyPath()) {
      validationError.setField(node.getName());
    }
    validationError.setCode(violation.getMessageTemplate());
    validationError.setDefaultMessage(violation.getMessage());
    validationError.setArguments(null);
    return validationError;
  }
}
