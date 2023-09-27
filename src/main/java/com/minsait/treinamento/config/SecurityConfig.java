package com.minsait.treinamento.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minsait.treinamento.exceptions.MensagemPersonalizada;
import com.minsait.treinamento.model.filters.security.FiltroAutenticacaoJwt;
import com.minsait.treinamento.model.filters.security.FiltroAutorizacaoJwt;
import com.minsait.treinamento.model.service.security.TokenJwtService;
import com.minsait.treinamento.model.service.security.UserDetailsServicePersonalizado;
import com.minsait.treinamento.utils.exceptions.ConfiguraRespostaException;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true, securedEnabled = true, prePostEnabled = true)
@ConditionalOnProperty(name = "security-ativo",prefix = "app.security", matchIfMissing = true)
@Slf4j
public class SecurityConfig {
    
    public static final String LOGOUT_URL = "/auth/logout";
    public static final String LOGIN_URL = "/auth/login";
    
    private final String [] URLS_SWAGGER = {"/swagger-resources/**",
                                                    "/swagger-ui**/**",
                                                    "/v2/api-docs/**",
                                                    "/v3/api-docs/**"};
    private final String [] URLS_PUBLICAS;
    
    private final  List<String> ORIGENS_PERMITIDAS;
    
    private final List<String> METODOS_PERMITIDOS;
    
    private final List<String> HEADERS_PERMITIDOS;
    
    private final List<String> HEADERS_EXPOSTOS;
    
    @Autowired
    private ObjectMapper mapper;
    
    @Autowired 
    private TokenJwtService tokenService;
    
    public SecurityConfig(@Value("${springfox.documentation.enabled:false}") 
                            boolean swaggerAtivo,
                          @Value("${app.security.urls-publicas:/}") 
                            List<String> urlsPublicas,
                          @Value("${app.security.origens-permitidas:*}") 
                            List<String> origensPermitidas,
                          @Value("${app.security.metodos-permitidos:GET,POST,PUT,DELETE,OPTIONS}") 
                            List<String> metodosPermitidos,
                          @Value("${app.security.headers.permitidos:Authorization,Cache-Control,Content-Type}") 
                            List<String> headersPermitidos,
                          @Value("${app.security.headers.expostos:Authorization,Cache-Control,Content-Type}") 
                            List<String> headersExpostos) {
        
        if(swaggerAtivo) {
            urlsPublicas.addAll(Arrays.asList(URLS_SWAGGER));
        }
        
        this.URLS_PUBLICAS = urlsPublicas.toArray(new String[0]);
        this.METODOS_PERMITIDOS = metodosPermitidos;
        this.ORIGENS_PERMITIDAS = origensPermitidas;
        this.HEADERS_PERMITIDOS = headersPermitidos;
        this.HEADERS_EXPOSTOS = headersExpostos;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, 
                                                   UserDetailsServicePersonalizado uDService,
                                                   ApplicationContext context,
                                                   AuthenticationManagerBuilder authBuilder) throws Exception {
        http.csrf().disable(); // Não vamos considerar Cross Site Request Forgery neste exemplo
        
        // Define urls publicas
        http.authorizeRequests().antMatchers(URLS_PUBLICAS).permitAll();
        
        // Qualquer outra url deve ser autenticada
        http.authorizeRequests().anyRequest().authenticated();
        
        // Configura o Cross Origin Resource Sharing
        http.cors().configurationSource(corsConfigSource());
        
        // Configura info de logout
        http.logout()
                .logoutUrl(LOGOUT_URL) // url de logout
                .addLogoutHandler(tratamentoLogout()) // Tratamento que acontece quando o logout é realizado
                .logoutSuccessHandler(trataSucessoLogout()); // O que fazer quando obter sucesso.
        
        // Tratamentos de erro nas requisições
        http.exceptionHandling().authenticationEntryPoint(tratamentoErroAcesso());
        http.exceptionHandling().accessDeniedHandler(tratamentoAcessoNegado());
        
        // Deixando a aplicação Stateless
        http.sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // Adiciona filtro de autenticação
        // Autenticação só ocorre quando o endpoint de login é chamado
        http.addFilter(new FiltroAutenticacaoJwt(context.getBean(AuthenticationManager.class),mapper, tokenService)/*,BearerTokenAuthenticationFilter.class*/);
        
        // Adiciona filtro de autorização
        http.addFilterAfter(FiltroAutorizacaoJwt.builder().mapper(mapper).tokenService(tokenService).URLS_PUBLICAS(URLS_PUBLICAS).uDService(uDService).build(),FiltroAutenticacaoJwt.class);
        
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> {
                    web.ignoring().antMatchers("/resources/**");
               };
    }
    


    private AccessDeniedHandler tratamentoAcessoNegado() {
         return (request, response,authException) -> {
                     ConfiguraRespostaException.setException(mapper, response, MensagemPersonalizada.ERRO_ACESSO_NEGADO, HttpStatus.UNAUTHORIZED);
                 };
     }

    private LogoutHandler tratamentoLogout() {
         return (request, response, authentication) -> {
                     String header = request.getHeader("Authorization");
                     if (header == null || !header.startsWith("Bearer ")) {
                         try {
                             ConfiguraRespostaException.setException(mapper, response, MensagemPersonalizada.ERRO_TOKEN_NAO_INFORMADO, HttpStatus.BAD_REQUEST);
                         } catch (IOException e) {
                             log.error(e.getMessage(),e);
                         }
                     }
                     try {
                         this.tokenService.remover(header.substring(7));
                     }
                     catch(Exception e) {
                         try {
                            ConfiguraRespostaException.setException(mapper, response, MensagemPersonalizada.ERRO_ACESSO_NEGADO, HttpStatus.UNAUTHORIZED);
                        } catch (IOException e1) {
                            log.error(e1.getMessage(),e1);
                        }
                     }
                 };
     }

     private LogoutSuccessHandler trataSucessoLogout() {
         return (request, response, authentication) -> {
                     // Remover tokens e etc
                     response.setStatus(HttpStatus.ACCEPTED.value());
                     
                 };
     }

     private CorsConfigurationSource corsConfigSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        
        corsConfig.setAllowedOrigins(this.ORIGENS_PERMITIDAS);
        corsConfig.setAllowedMethods(this.METODOS_PERMITIDOS);
    
        corsConfig.setAllowCredentials(true);
        corsConfig.setAllowedHeaders(this.HEADERS_PERMITIDOS);
        corsConfig.setExposedHeaders(this.HEADERS_EXPOSTOS);
        
        final UrlBasedCorsConfigurationSource corsConfigSource =  new UrlBasedCorsConfigurationSource();
        
        corsConfigSource.registerCorsConfiguration("/**", corsConfig);
        return corsConfigSource;
     }

     private AuthenticationEntryPoint tratamentoErroAcesso() {
         return (request, response,authException) -> {
             ConfiguraRespostaException.setException(mapper, response, MensagemPersonalizada.ERRO_ACESSO_NEGADO, HttpStatus.UNAUTHORIZED);
         };
     }
     
     @Bean
     public PasswordEncoder passwordEncoder() {
         return new BCryptPasswordEncoder();
     }
     
     @Bean
     public AuthenticationManager authManager(@Autowired PasswordEncoder pe,  AuthenticationManagerBuilder authBuilder) {
        return authBuilder.getOrBuild();
     }
}
