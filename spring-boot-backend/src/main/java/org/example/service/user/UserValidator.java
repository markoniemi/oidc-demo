package org.example.service.user;

import org.example.model.user.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return User.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    User user = (User) target;
    if (user == null) {
      errors.reject("user.null");
    }
    ValidationUtils.rejectIfEmpty(errors, "username", "field.required");
  }
}
