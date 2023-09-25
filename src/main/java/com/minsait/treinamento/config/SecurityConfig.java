package com.minsait.treinamento.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minsait.treinamento.dtos.exceptions.ExceptionDTO;
import com.minsait.treinamento.exceptions.MensagemPersonalizada;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true, securedEnabled = true, prePostEnabled = true)
@ConditionalOnProperty(name = "security-ativo",prefix = "treinamento", matchIfMissing = true)
public class SecurityConfig {
    
    private static final String [] URLS_SWAGGER = {"/swagger-resources/**",
                                                    "/swagger-ui**/**",
                                                    "/v2/api-docs/**",
                                                    "/v3/api-docs/**",
                                                    "/info/**"};
    @Autowired
    private ObjectMapper mapper;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeRequests().antMatchers(URLS_SWAGGER).permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.formLogin();
//        http.exceptionHandling().authenticationEntryPoint(AuthEntryPoint());
        return http.build();
    }

   private AuthenticationEntryPoint AuthEntryPoint() {
        // TODO Auto-generated method stub
        return (request, response,authException) -> {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(mapper.writeValueAsString(new ExceptionDTO(MensagemPersonalizada.ERRO_ACESSO_NEGADO,HttpStatus.UNAUTHORIZED.value())));
        };
    }

@Bean
   public WebSecurityCustomizer webSecurityCustomizer() {
       return (web) -> {
           web.ignoring().antMatchers("/resources/**");
           };
   }

}
