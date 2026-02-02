package org.example.service.user;

import java.util.List;
import org.example.model.user.User;

public interface UserService {
  List<User> findAll();

  List<User> search(UserSearchForm userSearchForm);

  User create(User user);

  User update(User user);

  User findById(Long id);

  User findByUsername(String username);

  boolean exists(Long id);

  void delete(Long id);

  long count();
}
