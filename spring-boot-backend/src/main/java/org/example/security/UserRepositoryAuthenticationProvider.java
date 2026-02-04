package org.example.security;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.example.model.user.User;
import org.example.service.user.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@RequiredArgsConstructor
public class UserRepositoryAuthenticationProvider implements AuthenticationProvider {
  private final UserService userService;

  /** Authenticate using UserRepository. */
  @Override
  public Authentication authenticate(Authentication authentication) {
    log.debug("authenticate {}", authentication.getName());
    User user = userService.findByUsername(authentication.getName());
    return authenticateUser(user, authentication);
  }

  private Authentication authenticateUser(User user, Authentication authentication) {
    if (user != null && authentication.getCredentials().equals(user.getPassword())) {
      List<GrantedAuthority> authorities = new ArrayList<>();
      authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
      return new UsernamePasswordAuthenticationToken(
          authentication.getName(), authentication.getCredentials(), authorities);
    } else {
      return null;
      // throw new BadCredentialsException("Authentication failed");
    }
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}
