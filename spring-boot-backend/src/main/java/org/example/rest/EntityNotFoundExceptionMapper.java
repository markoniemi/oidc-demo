package org.example.rest;

import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class EntityNotFoundExceptionMapper implements ExceptionMapper<EntityNotFoundException> {

  public Response toResponse(EntityNotFoundException e) {
    return Response.status(Response.Status.NOT_FOUND).build();
  }
}
