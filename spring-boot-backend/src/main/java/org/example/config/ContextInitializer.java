package org.example.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class ContextInitializer
    implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  public void initialize(ConfigurableApplicationContext applicationContext) {
    applicationContext
        .getEnvironment()
        .getPropertySources()
        .addFirst(new FixedPropertySource("fixedPropertySource"));
  }
}
