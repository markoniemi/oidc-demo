package org.example.service.user;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.example.model.user.User;
import org.example.repository.user.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import jakarta.annotation.Resource;
import jakarta.jws.WebService;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.xml.ws.WebServiceContext;
import lombok.extern.log4j.Log4j2;

@Primary
@Log4j2
@Component(value = "userService")
@WebService(endpointInterface = "org.example.service.user.UserService", serviceName = "UserService")
public class UserServiceImpl implements UserService {
  @Resource private UserRepository userRepository;
  @Resource WebServiceContext context;
  @Resource UserValidator userValidator;
  @Resource Validator validator;

  @Override
  @Transactional
  public List<User> findAll() {
    log.trace("findAll");
    return IterableUtils.toList(userRepository.findAll());
  }

  @Override
  @Transactional
  public List<User> find(UserSearchForm userSearchForm) {
    log.info("search: {}", userSearchForm);
    if (userSearchForm != null) {
      if (StringUtils.isNotBlank(userSearchForm.getEmail())) {
        return Arrays.asList(userRepository.findByEmail(userSearchForm.getEmail()));
      }
      if (StringUtils.isNotBlank(userSearchForm.getUsername())) {
        return Arrays.asList(userRepository.findByUsername(userSearchForm.getUsername()));
      }
    }
    return IterableUtils.toList(userRepository.findAll());
  }

  @Override
  @Transactional
  public User create(User user) throws BindException {
    BindException errors = new BindException(user, "user");
    userValidator.validate(user, errors);
    if (userRepository.findByUsername(user.getUsername()) != null)
      errors.reject("exist.user.username");
    if (errors.hasErrors()) {
      throw errors;
    }
    log.trace("create: {}", user);
    return userRepository.save(user);
  }

  @Override
  @Transactional
  public User update(User user) {
    User databaseUser = userRepository.findById(user.getId()).get();
    if (databaseUser == null) {
      throw new NotFoundException("User does not exist.");
    }
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    if (CollectionUtils.isNotEmpty(violations)) {
      throw new ConstraintViolationException(violations);
    }
    user.setId(databaseUser.getId());
    log.trace("update: {}", user);
    return userRepository.save(user);
  }

  @Override
  @Transactional
  public User findById(Long id) {
    log.trace("findById: {}", id);
    if (id == null) {
      throw new BadRequestException();
    }
    return userRepository.findById(id).get();
  }

  @Override
  @Transactional
  public User findByUsername(String username) {
    log.trace("findByUsername: {}", username);
    if (StringUtils.isBlank(username)) {
      throw new BadRequestException();
    }
    return userRepository.findByUsername(username);
  }

  @Override
  @Transactional
  public User findByEmail(String email) {
    log.trace("findByEmail: {}", email);
    return userRepository.findByEmail(email);
  }

  @Override
  @Transactional
  public boolean exists(Long id) {
    return userRepository.findById(id) != null;
  }

  @Override
  @Transactional
  /** If the entity is not found in the persistence store it is silently ignored. */
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
