package org.example.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

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
  KeycloakContainer keycloakContainer() {
    KeycloakContainer keycloak =
        new KeycloakContainer("quay.io/keycloak/keycloak:26.4")
            .withAdminUsername("admin")
            .withAdminPassword("admin")
            .withRealmImportFile("/realm-export.json")
            .withExposedPorts(8080,9000)
            .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(9090), new ExposedPort(8080)),new PortBinding(Ports.Binding.bindPort(9000), new ExposedPort(9000)))
            ));            
    keycloak.start();
    return keycloak;
  }
}
