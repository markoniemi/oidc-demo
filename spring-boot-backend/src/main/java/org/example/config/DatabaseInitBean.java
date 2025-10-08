package org.example.config;

import org.example.model.user.Role;
import org.example.model.user.User;
import org.example.repository.user.UserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class DatabaseInitBean implements InitializingBean {
  @Resource private UserRepository userRepository;

  @Value("${initial.username:admin}")
  private String username;

  @Override
  public void afterPropertiesSet() throws Exception {
    log.debug("Creating user {}", username);
    userRepository.save(new User(username, "admin", "email", Role.ROLE_ADMIN));
    for (int i = 0; i < 5; i++) {
      userRepository.save(new User("username" + i, "user", "email" + i, Role.ROLE_USER));
    }
  }
}
