package org.example;

import org.example.config.ContextInitializer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class OidcDemoApplication {
  public static void main(String[] args) {
    new SpringApplicationBuilder(OidcDemoApplication.class)
            .initializers(new ContextInitializer())
            .run(args);
  }
}
