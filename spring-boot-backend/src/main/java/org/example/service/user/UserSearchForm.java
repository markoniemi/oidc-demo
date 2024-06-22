package org.example.service.user;

import org.example.model.user.Role;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.QueryParam;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserSearchForm {
  @QueryParam("role")
  @FormParam("role")
  private Role role;
  @QueryParam("email")
  @FormParam("email")
  private String email;
  @QueryParam("username")
  @FormParam("username")
  private String username;
}
