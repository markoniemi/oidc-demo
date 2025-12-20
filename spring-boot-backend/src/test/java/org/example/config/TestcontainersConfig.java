package org.example.config;

import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.output.Slf4jLogConsumer;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import lombok.extern.slf4j.Slf4j;

@TestConfiguration(proxyBeanMethods = false)
@Slf4j
public class TestcontainersConfig {
  @Bean
  @RestartScope
  KeycloakContainer keycloakContainer() {
    KeycloakContainer keycloak =
        new KeycloakContainer("quay.io/keycloak/keycloak:26.4")
            .withAdminUsername("admin")
            .withAdminPassword("admin")
            .withRealmImportFile("/realm-export.json")
            .withExposedPorts(8080, 9000)
            //          .withDebug()
            //          .withDebugFixedPort(8787, false)
            .withEnv(
                "KC_LOG_LEVEL",
                "INFO,demo.keycloak.userstorage.ldap.attributeimport:DEBUG,org.example.keycloak:DEBUG")
            .withLogConsumer(new Slf4jLogConsumer(log))
            .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(createPortBindings()));
    keycloak.start();
    return keycloak;
  }

  private HostConfig createPortBindings() {
    return new HostConfig()
        .withPortBindings(
            new PortBinding(Ports.Binding.bindPort(9090), new ExposedPort(8080)),
            new PortBinding(Ports.Binding.bindPort(9000), new ExposedPort(9000)));
  }
}
