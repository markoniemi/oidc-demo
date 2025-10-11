package org.example.config;

import org.hsqldb.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("hsqldb")
public class HsqldbConfig {
  @Bean(initMethod = "start", destroyMethod = "stop")
  public Server hsqldbServer() {
      Server server = new Server();
      server.setDatabaseName(0, "testdb");
      server.setDatabasePath(0, "mem:testdb");
      server.setPort(9092);
      return server;
  }  
}
