package org.example.config;

import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@PropertySource("datasource-hsqldb-it.properties")
@Profile("hsqldb")
public class HsqldbIntegrationTestConfig {}
