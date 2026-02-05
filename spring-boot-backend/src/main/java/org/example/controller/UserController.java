package org.example.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.UserDto;
import org.example.log.InterfaceLog;
import org.example.mapper.UserMapper;
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

@Slf4j
@RestController
@RequestMapping("/api/rest/users")
@RequiredArgsConstructor
@InterfaceLog
public class UserController {
  private final UserService userService;
  private final UserMapper userMapper;

  @GetMapping({"", "/"})
  @InterfaceLog
  public List<UserDto> search(@ModelAttribute UserSearchForm searchForm) {
    log.debug("search: {}", searchForm);
    List<User> users;
    if (searchForm == null || searchForm.isEmpty()) {
      users = userService.findAll();
    } else {
      users = userService.search(searchForm);
    }
    return userMapper.toDtos(users);
  }

  @PostMapping
  @InterfaceLog
  public UserDto create(@Valid @RequestBody UserDto userDto) {
    log.debug("create: {}", userDto);
    User user = userMapper.toEntity(userDto);
    return userMapper.toDto(userService.create(user));
  }

  @PutMapping("/{id}")
  @InterfaceLog
  public UserDto update(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
    log.debug("update: {}, id: {}", userDto, id);
    userDto.setId(id);
    User user = userMapper.toEntity(userDto);
    return userMapper.toDto(userService.update(user));
  }

  @GetMapping("/{id}")
  @InterfaceLog
  public UserDto findById(@PathVariable Long id) {
    log.debug("findById: {}", id);
    return userMapper.toDto(userService.findById(id));
  }

  @GetMapping("/username/{username}")
  @InterfaceLog
  public UserDto findByUsername(@PathVariable String username) {
    log.debug("findByUsername: {}", username);
    return userMapper.toDto(userService.findByUsername(username));
  }

  @GetMapping("/exists/{id}")
  @InterfaceLog
  public boolean exists(@PathVariable Long id) {
    log.debug("exists: {}", id);
    return userService.exists(id);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @InterfaceLog
  public void delete(@PathVariable Long id) {
    log.debug("delete: {}", id);
    userService.delete(id);
  }

  @GetMapping("/count")
  @InterfaceLog
  public long count() {
    log.debug("count");
    return userService.count();
  }
}
