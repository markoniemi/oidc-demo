package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import java.util.Arrays;
import java.util.List;
import org.example.model.user.Role;
import org.example.model.user.User;
import org.example.service.user.UserRestClient;
import org.example.service.user.ValidationError;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class UserServiceRestIT extends AbstractIntegrationTestBase {
    private UserRestClient userService = new UserRestClient();

    @Test
    public void findAll() throws JsonProcessingException {
        List<User> users = userService.findAll();
        assertNotNull(users);
        assertEquals(6, users.size());
    }

    @Test
    public void find() throws JsonProcessingException {
        List<User> users = userService.findAll();
        assertNotNull(users);
        log.info(Arrays.toString(users.toArray()));
        assertEquals(6, users.size());
        users = userService.findByEmail("email0");
        assertNotNull(users);
        log.info(Arrays.toString(users.toArray()));
        assertEquals(1, users.size());
        assertEquals("email0", users.get(0).getEmail());
        users = userService.findByUsername("username0");
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("username0", users.get(0).getUsername());
    }

    @Test
    public void create() throws JsonProcessingException {
        User user = new User("username", "password", "email", Role.ROLE_USER);
        user = userService.create(user);
        assertNotNull(user);
        assertNotNull(user.getId());
        user = userService.find(user.getId());
        assertNotNull(user);
        assertNotNull(user.getId());
        userService.delete(user.getId());
    }

    @Test
    public void createWithValidationError() throws JsonProcessingException {
        String userJson = "{\"username\":null}";
        List<ValidationError> validationErrors = userService.create(userJson, BAD_REQUEST);
        assertEquals(1, validationErrors.size());
        ValidationError validationError = validationErrors.get(0);
        log.debug(validationError);
        assertEquals("user", validationError.getObjectName());
        assertEquals("username", validationError.getField());
        assertEquals("field.required", validationError.getCode());
    }

    @Test
    public void updateWithValidationError() throws JsonProcessingException {
        String userJson = "{\"id\":1, \"username\":null}";
        List<ValidationError> validationErrors = userService.update(userJson, 1, BAD_REQUEST);
        log.debug(Arrays.toString(validationErrors.toArray()));
        assertEquals(2, validationErrors.size());
        ValidationError validationError = validationErrors.get(0);
        assertEquals("User", validationError.getObjectName());
//        assertEquals("password", validationError.getField());
        assertEquals("field.required", validationError.getCode());
    }

    @Test
    public void deleteNonExistent() {
        userService.delete(1000L, NO_CONTENT);
    }
}
