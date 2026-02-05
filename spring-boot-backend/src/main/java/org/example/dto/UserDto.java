package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.example.model.user.Role;

@Data
@AllArgsConstructor
public class UserDto {
  private Long id;

  @NotBlank(message = "field.required")
  private String username;

  @ToString.Exclude private String password;

  @NotBlank(message = "field.required")
  private String email;

  @NotNull(message = "field.required")
  private Role role;
}
