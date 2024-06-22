package org.example.security;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private AuthenticationManager authenticationManager;

  public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    return authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(obtainUsername(request), obtainPassword(request)));
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res,
      FilterChain chain, Authentication auth) throws IOException, ServletException {
    String token = JwtToken.createToken(((User) auth.getPrincipal()).getUsername());
    res.addHeader(JwtToken.AUTHORIZATION_HEADER, JwtToken.TOKEN_PREFIX + token);
  }
}
