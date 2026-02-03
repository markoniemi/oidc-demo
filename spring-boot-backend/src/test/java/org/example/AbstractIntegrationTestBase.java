package org.example;

import org.example.config.H2IntegrationTestConfig;
import org.example.config.HsqldbIntegrationTestConfig;
import org.example.config.IntegrationTestConfig;
import org.example.config.TestcontainersConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;

/** Base class for integration tests, enables running multiple tests with @SpringBootTest */
@SpringBootTest(classes = OidcDemoApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@Import({
  IntegrationTestConfig.class,
  HsqldbIntegrationTestConfig.class,
  H2IntegrationTestConfig.class,
  TestcontainersConfig.class,
})

public abstract class AbstractIntegrationTestBase {}
