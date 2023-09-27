package com.minsait.treinamento.model.filters.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minsait.treinamento.config.SecurityConfig;
import com.minsait.treinamento.dtos.security.CredenciaisDTO;
import com.minsait.treinamento.exceptions.MensagemPersonalizada;
import com.minsait.treinamento.model.security.UsuarioAutenticador;
import com.minsait.treinamento.model.service.security.TokenJwtService;
import com.minsait.treinamento.utils.exceptions.ConfiguraRespostaException;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FiltroAutenticacaoJwt extends UsernamePasswordAuthenticationFilter {

    private ObjectMapper mapper;
    
    private TokenJwtService tokenService;
    
    public FiltroAutenticacaoJwt(AuthenticationManager authenticationManager, ObjectMapper mapper, TokenJwtService tokenService) {
        super(authenticationManager);
        this.mapper = mapper;
        this.tokenService = tokenService;
        this.setFilterProcessesUrl(SecurityConfig.LOGIN_URL);
        this.setUsernameParameter("usuario");
        this.setPasswordParameter("senha");
        this.setPostOnly(true);
    }




    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        CredenciaisDTO credenciais = null;
        
        String usuario = request.getParameter(this.getUsernameParameter());
        String senha = request.getParameter(this.getPasswordParameter());
        
        if(usuario == null || senha == null) {
            try {
                credenciais = mapper.readValue(request.getInputStream(), CredenciaisDTO.class);
            } catch (IOException e) {
                throw new BadCredentialsException(e.getMessage());
            }
        }
        else {
            credenciais = CredenciaisDTO.builder()
                                        .usuario(usuario)
                                        .senha(senha)
                                        .build();
        }
        if(credenciais.getUsuario() == null || credenciais.getSenha() == null)
            throw new BadCredentialsException(MensagemPersonalizada.ERRO_CREDENCIAIS_AUSENTES.getDescricaoMsg());
        
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(credenciais.getUsuario(), credenciais.getSenha());
        
        return this.getAuthenticationManager().authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        response.addHeader("Authorization", "Bearer " + this.tokenService.trataToken(((UsuarioAutenticador) authResult.getPrincipal()).getUsername(), authResult.getAuthorities()));
        response.addHeader("access-control-expose-headers", "Authorization");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        ConfiguraRespostaException.setException(mapper, response, MensagemPersonalizada.ERRO_ACESSO_NEGADO, HttpStatus.FORBIDDEN);
    }
    
    
    

}
