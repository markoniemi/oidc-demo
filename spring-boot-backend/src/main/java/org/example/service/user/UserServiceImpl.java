package org.example.service.user;

import java.util.List;

import org.apache.commons.collections4.IterableUtils;
import org.example.model.user.User;
import org.example.repository.user.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Primary
@Slf4j
@Service(value = "userService")
public class UserServiceImpl implements UserService {
  private UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @Transactional
  public List<User> findAll() {
    log.trace("findAll");
    return IterableUtils.toList(userRepository.findAll());
  }

  @Override
  @Transactional
  public List<User> search(UserSearchForm searchForm) {
    log.trace("search: {}", searchForm);
    if (searchForm == null || searchForm.isEmpty()) {
      return IterableUtils.toList(userRepository.findAll());
    }
    return userRepository.findByUsernameOrEmailOrRole(
        searchForm.getUsername(), searchForm.getEmail(), searchForm.getRole());
  }

  @Override
  @Transactional
  public User create(User user) {
    if (user == null) {
      throw new IllegalArgumentException("User cannot be null");
    }
    if (userRepository.existsByUsername(user.getUsername())) {
      throw new IllegalArgumentException("Username already exists");
    }
    log.trace("create: {}", user);
    return userRepository.save(user);
  }

  @Override
  @Transactional
  public User update(User user) {
    if (user == null) {
      throw new IllegalArgumentException("User cannot be null");
    }
    if (!userRepository.existsById(user.getId())) {
      throw new IllegalArgumentException("User not found");
    }
    log.trace("update: {}", user);
    return userRepository.save(user);
  }

  @Override
  @Transactional
  public User findById(Long id) {
    log.trace("findById: {}", id);
    if (id == null) {
      throw new IllegalArgumentException("ID cannot be null");
    }
    return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
  }

  @Override
  @Transactional
  public User findByUsername(String username) {
    log.trace("findByUsername: {}", username);
    return userRepository.findByUsername(username);
  }

  @Override
  @Transactional
  public boolean exists(Long id) {
    log.trace("exists: {}", id);
    return userRepository.existsById(id);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    log.trace("delete: {}", id);
    userRepository.deleteById(id);
  }

  @Override
  @Transactional
  public long count() {
    return userRepository.count();
  }
}
