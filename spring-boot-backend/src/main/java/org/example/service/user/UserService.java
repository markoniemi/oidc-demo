package org.example.service.user;

import java.util.List;
import org.example.model.user.User;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;

@Validated
public interface UserService {
  /**
   * @return all users from repository or an empty list in case of no items.
   */
  List<User> findAll();

  List<User> search(UserSearchForm userSearchForm);

  /**
   * Creates a user to repository.
   */
  User create(@Valid User user);

  /**
   * Updates a user in repository.
   */
  User update(@Valid User user);

  User findById(Long id);

  /**
   * @return user by username, or null if user does not exist
   */
  User findByUsername(String username);

  /**
   * @return true if a user by username exists.
   */
  boolean exists(Long id);

  /** Deletes a user by username. */
  void delete(Long id);

  /**
   * @return the count of users in repository.
   */
  long count();
}
