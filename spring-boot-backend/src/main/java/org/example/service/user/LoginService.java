package org.example.service.user;

import javax.naming.AuthenticationException;
import lombok.extern.log4j.Log4j2;
import org.example.log.InterfaceLog;
import org.example.model.user.User;
import org.example.security.JwtToken;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@InterfaceLog
public class LoginService {
  private final UserService userService;

  public LoginService(UserService userService) {
    this.userService = userService;
  }

  public String login(User userToLogin) throws AuthenticationException {
    User user = userService.findByUsername(userToLogin.getUsername());
    if (user == null) {
      throw new AuthenticationException("Login error");
    }
    if (user.getPassword().equals(userToLogin.getPassword())) {
      log.debug("Username: {} logged in.", user.getUsername());
      return JwtToken.create(user.getUsername());
    } else {
      throw new AuthenticationException("Login error");
    }
  }

  public void logout(String authenticationToken) {
    log.debug(authenticationToken);
  }
}
