package org.example.security;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
  public static final String TOKEN_PREFIX = "Bearer ";
  public JwtAuthorizationFilter(AuthenticationManager authManager) {
    super(authManager);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws IOException, ServletException {
    UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(getToken(request));
    if (authenticationToken != null) {
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
    chain.doFilter(request, response);
  }

  public static String getToken(HttpServletRequest request) {
    String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (!hasToken(header)) {
      log.debug("No token in Authorization header.");
      return null;
    }
    return header.replace(TOKEN_PREFIX, "");
  }

  private UsernamePasswordAuthenticationToken getAuthentication(String token) {
    if (StringUtils.isBlank(token)) {
      return null;
    }
    String user = JwtToken.verifyToken(token);
    if (StringUtils.isBlank(user)) {
      return null;
    }
    return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
  }
  public static boolean hasToken(String header) {
    return StringUtils.startsWith(header, TOKEN_PREFIX);
  }
}
