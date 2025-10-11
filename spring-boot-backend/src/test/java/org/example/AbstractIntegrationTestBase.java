package org.example;

import org.example.config.HsqldbIntegrationTestConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import com.github.springtestdbunit.DbUnitTestExecutionListener;

/** Base class for integration tests, enables running multiple tests with @SpringBootTest */
@SpringBootTest(classes = OidcDemoApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@ExtendWith(SpringExtension.class)
@ContextHierarchy(@ContextConfiguration(classes = HsqldbIntegrationTestConfig.class))
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    DbUnitTestExecutionListener.class})
public abstract class AbstractIntegrationTestBase {
}
