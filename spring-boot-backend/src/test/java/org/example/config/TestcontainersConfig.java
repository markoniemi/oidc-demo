package org.example.config;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;

@TestConfiguration(proxyBeanMethods = false)
@Slf4j
public class TestcontainersConfig {
  @Bean
  @RestartScope
  GenericContainer<?> authorizationServerContainer() {
    GenericContainer<?> authorizationServer =
        new GenericContainer<>("markoniemi/oidc-server:latest")
            .withExposedPorts(9090)
            .withLogConsumer(new Slf4jLogConsumer(log))
            .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(createPortBindings()));
    authorizationServer.waitingFor(Wait.forHttp("/"));
    authorizationServer.start();
    return authorizationServer;
  }

  private HostConfig createPortBindings() {
    return new HostConfig()
        .withPortBindings(new PortBinding(Ports.Binding.bindPort(9090), new ExposedPort(9090)));
  }
}
