package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import javax.naming.AuthenticationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.dto.LoginForm;
import org.example.log.InterfaceLog;
import org.example.security.JwtToken;
import org.example.service.user.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api/rest/auth")
@RequiredArgsConstructor
@InterfaceLog
public class LoginController {
  private final LoginService loginService;

  @PostMapping("/login")
  @InterfaceLog
  public String login(@RequestBody LoginForm loginForm) throws AuthenticationException {
    return loginService.login(loginForm);
  }

  @PostMapping("/logout")
  @InterfaceLog
  public void logout(HttpServletRequest request) {
    String authenticationToken = request.getHeader(JwtToken.AUTHORIZATION_HEADER);
    loginService.logout(authenticationToken);
  }
}
