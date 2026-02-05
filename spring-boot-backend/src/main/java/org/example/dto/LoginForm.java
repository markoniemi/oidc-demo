package org.example.dto;

import lombok.ToString;
import lombok.Value;

@Value
public class LoginForm {
  String username;
  @ToString.Exclude String password;
}
