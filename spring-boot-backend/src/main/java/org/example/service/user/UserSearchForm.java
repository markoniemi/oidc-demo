package org.example.service.user;

import org.apache.commons.lang3.StringUtils;
import org.example.model.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchForm {
  String username;

  String email;

  Role role;

  public boolean isEmpty() {
    if (StringUtils.isEmpty(username) && StringUtils.isEmpty(email) && role == null) {
      return true;
    }
    return false;
  }
}
