package org.example.service.user;

import javax.naming.AuthenticationException;
import org.example.model.user.User;

public interface LoginService {

  String login(User userToLogin) throws AuthenticationException;

  void logout(String authenticationToken);
}
