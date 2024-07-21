package org.example.service.user;

import javax.naming.AuthenticationException;
import org.example.model.user.User;
import org.example.security.JwtToken;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Produces({MediaType.APPLICATION_JSON})
@Component(value = "loginService")
@Path("/auth")
public class LoginServiceImpl implements LoginService {
  @Resource
  @Getter
  @Setter
  // private UserService userService = new UserServiceImpl();
  private UserService userService;

  @Override
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/login")
  public String login(User userToLogin) throws AuthenticationException {
    User user = userService.findByUsername(userToLogin.getUsername());
    if (user == null) {
      throw new AuthenticationException("Login error");
    }
    if (user.getPassword().equals(userToLogin.getPassword())) {
      log.debug("Username: {} logged in.", user.getUsername());
      return JwtToken.createToken(user.getUsername());
    } else {
      throw new AuthenticationException("Login error");
    }
  }

  @Override
  @POST
  @Path("/logout")
  public void logout(@Context HttpServletRequest request) {
    String authenticationToken = (String) request.getHeader(HttpHeaders.AUTHORIZATION);
    log.debug(authenticationToken);
  }
}
