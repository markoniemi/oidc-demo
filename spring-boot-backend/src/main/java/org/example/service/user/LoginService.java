package org.example.service.user;

import javax.naming.AuthenticationException;
import org.example.model.user.User;
import jakarta.servlet.http.HttpServletRequest;

public interface LoginService {

  String login(User userToLogin) throws AuthenticationException;

  void logout(HttpServletRequest request);

}
