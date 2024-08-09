package org.example.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Log4j2
public class FixedEnvironmentRepository implements EnvironmentRepository, Ordered {
  @Override
  public Environment findOne(String application, String profile, String label) {
    log.debug("FixedEnvironmentRepository findOne");
    return null;
  }

  @Override
  public int getOrder() {
    return 0;
  }
}
