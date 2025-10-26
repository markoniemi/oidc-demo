package org.example;

import org.example.config.TestcontainersConfig;
import org.springframework.boot.SpringApplication;

public class OidcDemoDevApplication {
  public static void main(String[] args) {
    SpringApplication.from(OidcDemoApplication::main).with(TestcontainersConfig.class).run(args);
  }
}
