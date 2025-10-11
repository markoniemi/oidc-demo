package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import jakarta.validation.Validator;

@Configuration
public class TestConfig {
  @Bean
  public Validator localValidatorFactoryBean() {
     return new LocalValidatorFactoryBean();
  }  
}
