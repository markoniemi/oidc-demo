package org.example.dto;

import lombok.Data;
import org.example.model.user.Role;

@Data
public class UserDTO {
  private Long id;
  private String username;
  private String email;
  private Role role;
}
