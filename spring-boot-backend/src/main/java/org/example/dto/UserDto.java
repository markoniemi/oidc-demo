package org.example.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.model.user.Role;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@JsonDeserialize(builder = UserDto.UserDtoBuilder.class)
public class UserDto {
  private final Long id;
  private final String username;
  private final String email;
  private final Role role;
}
