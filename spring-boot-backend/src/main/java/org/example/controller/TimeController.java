package org.example.controller;

import java.util.Date;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api/rest/time")
public class TimeController {

  @PostMapping
  public String getTime(@RequestBody String name) {
    log.debug("getTime called with: {}", name);
    return String.format("%s", new Date());
  }
}
