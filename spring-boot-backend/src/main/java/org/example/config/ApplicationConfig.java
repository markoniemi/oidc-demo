package org.example.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "org.example")
@EntityScan(basePackages = "org.example.model")
@Import({JpaConfig.class, WebServiceConfig.class, RestConfig.class})
public class ApplicationConfig {}
