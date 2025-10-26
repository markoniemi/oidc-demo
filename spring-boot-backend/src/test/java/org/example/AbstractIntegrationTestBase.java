package org.example;

import org.example.config.HsqldbIntegrationTestConfig;
import org.example.config.TestcontainersConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;

/** Base class for integration tests, enables running multiple tests with @SpringBootTest */
@SpringBootTest(classes = OidcDemoApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@Import({HsqldbIntegrationTestConfig.class, TestcontainersConfig.class})
@TestExecutionListeners({
  DependencyInjectionTestExecutionListener.class,
  DbUnitTestExecutionListener.class
})
public abstract class AbstractIntegrationTestBase {}
