package org.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/**").addResourceLocations(
        "classpath:/META-INF/resources/webjars/react-frontend/0.0.1-SNAPSHOT/");
  }

  // @Override
  // public void addViewControllers(ViewControllerRegistry registry) {
  // registry.addViewController("/").setViewName("forward:/index.html");
  // registry.addViewController("/{path:[^\\.]*}").setViewName("forward:/index.html");
  // registry.addViewController("/**/{path:[^\\.]*}").setViewName("forward:/index.html");
  // registry.addViewController("/**/{path:[^\\.]*}/**").setViewName("forward:/index.html");
  // }

  // @Override
  // public void addViewControllers(ViewControllerRegistry registry) {
  // registry.addViewController("/").setViewName("forward:/index.html");
  // registry.addViewController("/{spring:\\w+}").setViewName("forward:/index.html");
  // }

  // @Override
  // public void addViewControllers(ViewControllerRegistry registry) {
  // // registry.addViewController("/{x:^(?!api$).*$}")
  // // .setViewName("forward:/index.html");
  // // registry.addViewController("/**/{spring:\\w+}")
  // // .setViewName("forward:/");
  // // registry.addViewController("/{spring:\\w+}/**{spring:?!(\\.js|\\.css)$}")
  // // .setViewName("forward:/");
  // }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("forward:/index.html");
    registry.addViewController("/login").setViewName("forward:/index.html");
    registry.addViewController("/users/**").setViewName("forward:/index.html");
  }

  // @Override
  // public void addViewControllers(ViewControllerRegistry registry) {
  // direct all paths except /api to index.html
  // this does not work in spring 2.6 unless spring.mvc.pathmatch.matching-strategy=ant_path_matcher
  // is set in application.properties
  // registry.addViewController("/{x:[\\w\\-]+}").setViewName("forward:/index.html");
  // registry.addViewController("/{x:^(?!api$).*$}/**/{y:[\\w\\-]+}/**")
  // .setViewName("forward:/index.html");
  // }
}
