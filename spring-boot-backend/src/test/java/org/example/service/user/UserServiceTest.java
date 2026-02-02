package org.example.service.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.List;
import org.example.model.user.Role;
import org.example.model.user.User;
import org.example.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserServiceImpl userService;

  private User user;

  @BeforeEach
  void setUp() {
    user = new User("username", "password", "email", Role.ROLE_USER);
    user.setId(1L);
  }

  @Test
  void create() {
    when(userRepository.existsByUsername(anyString())).thenReturn(false);
    when(userRepository.save(any(User.class))).thenReturn(user);

    User createdUser = userService.create(new User("username", "password", "email", Role.ROLE_USER));
    
    assertEquals(user, createdUser);
    verify(userRepository).save(any(User.class));
  }

  @Test
  void createWithNullUser() {
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> userService.create(null));
    assertEquals("User cannot be null", exception.getMessage());
  }

  @Test
  void createWithExistingUser() {
    when(userRepository.existsByUsername("username")).thenReturn(true);
    
    User existingUser = new User("username", "password", "email", Role.ROLE_USER);
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> userService.create(existingUser));
    assertEquals("Username already exists", exception.getMessage());
  }

  @Test
  void update() {
    when(userRepository.existsById(1L)).thenReturn(true);
    when(userRepository.save(any(User.class))).thenReturn(user);

    user.setEmail("new email");
    User updatedUser = userService.update(user);

    assertEquals("new email", updatedUser.getEmail());
    verify(userRepository).save(user);
  }

  @Test
  void updateWithNullUser() {
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> userService.update(null));
    assertEquals("User cannot be null", exception.getMessage());
  }

  @Test
  void updateWithNonexistingUser() {
    when(userRepository.existsById(1L)).thenReturn(false);
    
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> userService.update(user));
    assertEquals("User not found", exception.getMessage());
  }

  @Test
  void search() {
    User user1 = new User("username1", "password", "email1", Role.ROLE_USER);
    User user2 = new User("username2", "password", "email2", Role.ROLE_ADMIN);
    when(userRepository.findByUsernameOrEmailOrRole("username1", null, null)).thenReturn(List.of(user1));

    UserSearchForm form = new UserSearchForm("username1", null, null);
    List<User> users = userService.search(form);
    
    assertEquals(1, users.size());
    assertEquals("username1", users.get(0).getUsername());
  }

  @Test
  void searchWithEmptyForm() {
    when(userRepository.findAll()).thenReturn(List.of(user, new User()));

    List<User> users = userService.search(new UserSearchForm());
    assertEquals(2, users.size());
  }
}
