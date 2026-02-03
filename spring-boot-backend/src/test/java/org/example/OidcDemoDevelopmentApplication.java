package org.example;
import org.example.config.ApplicationConfig;
import org.example.config.TestcontainersConfig;
import org.springframework.boot.SpringApplication;

public class OidcDemoDevelopmentApplication {
  public static void main(String[] args) {
    SpringApplication.from(OidcDemoApplication::main)
        .with(ApplicationConfig.class, TestcontainersConfig.class)
            .withAdditionalProfiles("h2","disableSecurity")
        .run(args);
  }
}
