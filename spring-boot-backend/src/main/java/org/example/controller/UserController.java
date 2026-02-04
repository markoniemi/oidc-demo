package org.example.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.model.user.User;
import org.example.service.user.UserSearchForm;
import org.example.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api/rest/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping({"", "/"})
  public List<User> search(@ModelAttribute UserSearchForm searchForm) {
    log.debug("search: {}", searchForm);
    if (searchForm == null || searchForm.isEmpty()) {
      return userService.findAll();
    }
    return userService.search(searchForm);
  }

  @PostMapping
  public User create(@Valid @RequestBody User user) {
    log.debug("create: {}", user);
    return userService.create(user);
  }

  @PutMapping("/{id}")
  public User update(@PathVariable Long id, @Valid @RequestBody User user) {
    log.debug("update: {}, id: {}", user, id);
    return userService.update(user);
  }

  @GetMapping("/{id}")
  public User findById(@PathVariable Long id) {
    log.debug("findById: {}", id);
    return userService.findById(id);
  }

  @GetMapping("/username/{username}")
  public User findByUsername(@PathVariable String username) {
    log.debug("findByUsername: {}", username);
    return userService.findByUsername(username);
  }

  @GetMapping("/exists/{id}")
  public boolean exists(@PathVariable Long id) {
    log.debug("exists: {}", id);
    return userService.exists(id);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    log.debug("delete: {}", id);
    userService.delete(id);
  }

  @GetMapping("/count")
  public long count() {
    log.debug("count");
    return userService.count();
  }
}
