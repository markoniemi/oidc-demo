package org.example.repository.user;

import java.util.List;
import org.example.model.user.Role;
import org.example.model.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
  User findByUsername(@Param("username") String username);

  boolean existsByUsername(@Param("username") String username);

  List<User> findByUsernameOrEmailOrRole(
      @Param("username") String username, @Param("email") String email, @Param("role") Role role);
}
