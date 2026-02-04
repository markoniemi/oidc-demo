package org.example.service.user;

import java.util.Date;
import org.springframework.stereotype.Service;

@Service
public class TimeService {
  public String getTime(String name) {
    return String.format("%s", new Date());
  }
}
