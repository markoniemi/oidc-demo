package org.example.service.user;

import javax.naming.AuthenticationException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.dto.LoginForm;
import org.example.model.user.User;
import org.example.security.JwtToken;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class LoginService {
  private final UserService userService;

  public String login(LoginForm loginForm) throws AuthenticationException {
    User user = userService.findByUsername(loginForm.getUsername());
    if (user == null) {
      throw new AuthenticationException("Login error");
    }
    if (user.getPassword().equals(loginForm.getPassword())) {
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
