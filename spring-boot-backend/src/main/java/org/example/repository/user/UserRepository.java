package org.example.repository.user;

import org.example.model.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
  User findByEmail(@Param("email") String email);

  User findByUsername(@Param("username") String username);
}
