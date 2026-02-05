package org.example.config;

import jakarta.validation.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class TestConfig {
  @Bean
  public Validator localValidatorFactoryBean() {
     return new LocalValidatorFactoryBean();
  }  
}
