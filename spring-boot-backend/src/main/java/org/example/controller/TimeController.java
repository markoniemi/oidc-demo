package org.example.controller;

import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/rest/time")
public class TimeController {

  @PostMapping
  public String getTime(@RequestBody String name) {
    log.debug("getTime called with: {}", name);
    return String.format("%s", new Date());
  }
}
