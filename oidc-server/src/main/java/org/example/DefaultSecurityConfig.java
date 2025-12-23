package org.example;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.token.DelegatingOAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.JwtGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableWebSecurity
public class DefaultSecurityConfig {
  @Bean
  @Order(1)
  SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
    OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
    http.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(withDefaults());
    return http.formLogin(withDefaults()).cors(withDefaults()).build();
  }

  @Bean
  @Order(2)
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    return http.cors(withDefaults())
        .authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
        .formLogin(withDefaults())
        .build();
  }

  @Bean
  UserDetailsService users() {
    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    UserDetails user =
        User.builder()
            .username("admin")
            .password("admin")
            .passwordEncoder(encoder::encode)
            .roles("USER")
            .build();
    return new InMemoryUserDetailsManager(user);
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");
    config.addAllowedOrigin("http://localhost:8081");
    config.setAllowCredentials(true);
    source.registerCorsConfiguration("/**", config);
    return source;
  }
  @Bean
  OAuth2TokenGenerator<OAuth2Token> tokenGenerator(JWKSource<SecurityContext> jwkSource) {
      JwtEncoder jwtEncoder = new NimbusJwtEncoder(jwkSource);
      JwtGenerator jwtAccessTokenGenerator = new JwtGenerator(jwtEncoder);
//      jwtAccessTokenGenerator.setJwtCustomizer(oauth2AccessTokenCustomizer); // jwt customizer from part 1 (optional)
   
      return new DelegatingOAuth2TokenGenerator(
                        jwtAccessTokenGenerator,
                        new OAuth2PublicClientRefreshTokenGenerator() // add customized refresh token generator
      ); 
  }  
}
