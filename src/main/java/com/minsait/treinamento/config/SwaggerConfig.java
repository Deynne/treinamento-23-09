package com.minsait.treinamento.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public Docket swaggerDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfo())
                    .securitySchemes(Arrays.asList(esquemaSeguranca()))
                    .securityContexts(Arrays.asList(contextoSeguranca()))
                    .select()
                    .apis(RequestHandlerSelectors.any())
                    .paths(PathSelectors.any())
                    .build();
    }
    
    private ApiInfo apiInfo() {
        return new ApiInfo("Swagger Treinamento", 
                           "Swagger para teste dos endpoints criados ao longo do treinamento", 
                           "v1.0", 
                           null, // Link dos termos de uso 
                           new Contact("Deynne de Andrade Silva", 
                                       "https://github.com/Deynne", 
                                       "dandrades@minsait.com"), 
                           null, // Licensa utilizada
                           null, // Url da licensa utilizada
                           Collections.emptyList());
    }
    
    private SecurityContext contextoSeguranca() {
        return SecurityContext.builder()
                  .securityReferences(Arrays.asList(new SecurityReference("JWT", escopos())))
                  .build();
    }
    
    private SecurityScheme esquemaSeguranca() {
        
        SecurityScheme auth = new ApiKey("JWT", "Authorization", "header");
        return auth;
    }
    
    private AuthorizationScope[] escopos() {
        AuthorizationScope[] scopes = { 
              new AuthorizationScope("global", "accessEverything")
                  };
        return scopes;
    }

}
