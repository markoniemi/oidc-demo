package org.example.config;

import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@PropertySource("datasource-h2-it.properties")
@Profile("h2")
public class H2IntegrationTestConfig {}
