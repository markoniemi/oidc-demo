package org.example.config;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.example.service.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.Resource;
import jakarta.xml.ws.Endpoint;

@Configuration
public class WebServiceConfig {
  private UserService userService;
  private Bus bus;

  public WebServiceConfig(Bus bus, UserService userService) {
    this.bus = bus;
    this.userService = userService;
  }

  @Bean
  public Endpoint endpoint() {
    EndpointImpl endpoint = new EndpointImpl(bus, userService);
    endpoint.setAddress("/soap");
    endpoint.publish("/soap");
    return endpoint;
  }
}
