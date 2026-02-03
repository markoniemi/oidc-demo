package org.example.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.dto.UserCreateDTO;
import org.example.dto.UserDTO;
import org.example.dto.UserUpdateDTO;
import org.example.mapper.UserMapper;
import org.example.model.user.User;
import org.example.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final UserMapper userMapper;

  @GetMapping
//  @PreAuthorize("hasAuthority('SCOPE_read')")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<List<UserDTO>> getAllUsers() {
    List<UserDTO> users = userService.findAll().stream()
        .map(userMapper::toDTO)
        .collect(Collectors.toList());
    return ResponseEntity.ok(users);
  }

  @GetMapping(value = "/{id}", produces = "application/json")
//  @PreAuthorize("hasAuthority('SCOPE_read')")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) {
    User user = userService.findById(id);
    return ResponseEntity.ok(userMapper.toDTO(user));
  }

  @PostMapping
//  @PreAuthorize("hasAuthority('SCOPE_write')")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserCreateDTO createDTO) {
    User user = userMapper.toEntity(createDTO);
    User createdUser = userService.create(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toDTO(createdUser));
  }

  @PutMapping("/{id}")
//  @PreAuthorize("hasAuthority('SCOPE_write')")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserUpdateDTO updateDTO) {
    User user = userService.findById(id);
    userMapper.updateEntityFromDTO(updateDTO, user);
    User updatedUser = userService.update(user);
    return ResponseEntity.ok(userMapper.toDTO(updatedUser));
  }

  @DeleteMapping("/{id}")
//  @PreAuthorize("hasAuthority('SCOPE_write')")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
    userService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
