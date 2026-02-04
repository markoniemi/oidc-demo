package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import javax.naming.AuthenticationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.model.user.User;
import org.example.security.JwtToken;
import org.example.service.user.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api/rest/auth")
@RequiredArgsConstructor
public class LoginController {
  private final UserService userService;

  @PostMapping("/login")
  public String login(@RequestBody User userToLogin) throws AuthenticationException {
    log.debug("Login attempt for user: {}", userToLogin.getUsername());
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

  @PostMapping("/logout")
  public void logout(HttpServletRequest request) {
    String authenticationToken = request.getHeader(JwtToken.AUTHORIZATION_HEADER);
    log.debug("Logout with token: {}", authenticationToken);
  }
}
