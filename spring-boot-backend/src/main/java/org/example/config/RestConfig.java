package org.example.config;

import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.example.rest.BindExceptionMapper;
import org.example.rest.ConstraintViolationExceptionMapper;
import org.example.rest.EntityNotFoundExceptionMapper;
import org.example.service.user.LoginService;
import org.example.service.user.TimeService;
import org.example.service.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.jakarta.rs.json.JacksonXmlBindJsonProvider;
import jakarta.annotation.Resource;

@Configuration
public class RestConfig {
  @Resource private UserService userService;
  @Resource private TimeService helloService;
  @Resource private LoginService loginService;
  @Resource private Bus bus;

  @Bean(destroyMethod = "destroy")
  public Server jaxRsServer() {
    JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
    factory.setProvider(new JacksonXmlBindJsonProvider());
    factory.setProvider(new BindExceptionMapper());
    factory.setProvider(new EntityNotFoundExceptionMapper());
    factory.setProvider(new ConstraintViolationExceptionMapper());
    factory.setBus(bus);
    factory.setAddress("/rest");
    factory.setServiceBeanObjects(userService, helloService, loginService);
    return factory.create();
  }
}
