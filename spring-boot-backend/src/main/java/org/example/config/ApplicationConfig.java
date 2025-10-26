package org.example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "org.example")
@Import({JpaConfig.class, H2Config.class, WebServiceConfig.class, RestConfig.class})
public class ApplicationConfig {}
