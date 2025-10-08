package org.example;

import org.example.config.IntegrationTestConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/** Base class for integration tests, enables running multiple tests with @SpringBootTest */
@SpringBootTest(classes = OidcDemoApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@ExtendWith(SpringExtension.class)
@ContextHierarchy(@ContextConfiguration(classes = IntegrationTestConfig.class))
public abstract class AbstractIntegrationTestBase {}
