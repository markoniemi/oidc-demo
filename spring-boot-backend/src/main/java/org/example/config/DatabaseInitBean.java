package org.example.config;

import lombok.extern.slf4j.Slf4j;
import org.example.model.user.Role;
import org.example.model.user.User;
import org.example.repository.user.UserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DatabaseInitBean implements InitializingBean {
  private final UserRepository userRepository;

  @Value("${initial.username:admin}")
  private String username;

  public DatabaseInitBean(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void afterPropertiesSet() {
    log.debug("Creating user {}", username);
    userRepository.save(new User(username, "admin", "email", Role.ROLE_ADMIN));
    for (int i = 0; i < 5; i++) {
      userRepository.save(new User("username" + i, "user", "email" + i, Role.ROLE_USER));
    }
  }
}
