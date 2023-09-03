package com.minsait.treinamento.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Component
public class WebConfig extends WebMvcConfigurationSupport {
  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/swagger-ui/**").addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/").resourceChain(false);
  }

  @Override
  public void addViewControllers(final ViewControllerRegistry registry) {
	  String swaggerIndex = "/swagger-ui/index.html";
	  registry.addRedirectViewController("/", swaggerIndex);
	  registry.addRedirectViewController("/swagger-ui", swaggerIndex);
  }
}