package org.example.config;

import com.fasterxml.jackson.jakarta.rs.json.JacksonXmlBindJsonProvider;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.example.rest.BindExceptionMapper;
import org.example.rest.ConstraintViolationExceptionMapper;
import org.example.rest.EntityNotFoundExceptionMapper;
import org.example.service.user.LoginService;
import org.example.service.user.TimeService;
import org.example.service.user.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Log4j2
public class RestConfig {
  @Resource
  private UserService userService;
  @Resource
  private TimeService helloService;
  @Resource
  private LoginService loginService;
  @Resource
  private Bus bus;
  @Value("${test.value}")
  private String testValue;

  @Bean(destroyMethod = "destroy")
  public Server jaxRsServer() {
    log.debug(testValue);
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
