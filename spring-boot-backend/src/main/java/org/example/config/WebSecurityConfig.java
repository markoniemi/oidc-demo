package org.example.config;

import org.example.security.JwtAuthenticationFilter;
import org.example.security.JwtAuthorizationFilter;
import org.example.security.UserRepositoryAuthenticationProvider;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import jakarta.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
  @Resource
  UserRepositoryAuthenticationProvider userRepositoryAuthenticationProvider;
  @Resource
  UserDetailsService userDetailsService;
  AuthenticationManager authenticationManager;
  String[] ignoredPaths =
      {"/*", "/login", "/api/rest/auth/login/**", "/h2-console/**", "/users/**"};

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http,
      UserDetailsService userDetailsService) throws Exception {
    AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
    builder.userDetailsService(userDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    authenticationManager = builder.build();
    return authenticationManager;
  }

  @DependsOn("authenticationManager")
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable);
    http.authorizeHttpRequests((auth)->auth.requestMatchers(RegexRequestMatcher.regexMatcher(".*\\?wsdl")).permitAll());
    http.authorizeHttpRequests((auth)->auth.requestMatchers(ignoredPaths).permitAll());
    http.authorizeHttpRequests((auth)->auth.anyRequest().authenticated());
    http.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));
//    http.addFilter(new JwtAuthenticationFilter(authenticationManager));
//    http.addFilter(new JwtAuthorizationFilter(authenticationManager));
//    http.sessionManagement((auth)->auth.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    return http.build();
  }
  @Bean
  public JwtDecoder jwtDecoder(OAuth2ResourceServerProperties properties) {
      return JwtDecoders.fromIssuerLocation(properties.getJwt().getIssuerUri());
  }  
}
