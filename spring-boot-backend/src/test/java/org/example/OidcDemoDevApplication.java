package org.example;

import org.example.config.LocalDevelopmentConfig;
import org.springframework.boot.SpringApplication;

public class OidcDemoDevApplication {    
    public static void main(String[] args) {
        SpringApplication.from(OidcDemoApplication::main)
          .with(LocalDevelopmentConfig.class)
          .run(args);
    }
}