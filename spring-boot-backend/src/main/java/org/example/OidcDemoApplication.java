package org.example;

import org.example.config.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OidcDemoApplication {
  public static void main(String[] args) {
    SpringApplication.run(ApplicationConfig.class, args);
  }
}
