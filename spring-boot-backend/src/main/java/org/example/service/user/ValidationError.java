package org.example.service.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationError {
  private String objectName;
  private String field;
  private String code;
  private String defaultMessage;
  private Object[] arguments;
}
