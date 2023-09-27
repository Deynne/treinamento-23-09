package com.minsait.treinamento.model.filters.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minsait.treinamento.exceptions.MensagemPersonalizada;
import com.minsait.treinamento.model.service.security.TokenJwtService;
import com.minsait.treinamento.utils.exceptions.ConfiguraRespostaException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;

@AllArgsConstructor
@Builder
public class FiltroAutorizacaoJwt extends OncePerRequestFilter {

    private ObjectMapper mapper;
    
    private TokenJwtService tokenService;
    
    private UserDetailsService uDService;
    
    private final String[] URLS_PUBLICAS;

    @Default
    private AntPathMatcher matcher = new AntPathMatcher();
    

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            UsernamePasswordAuthenticationToken auth = getAutenticacao(header.substring(7));
            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            else {
                ConfiguraRespostaException.setException(mapper, response, MensagemPersonalizada.ERRO_TOKEN_INVALIDO, HttpStatus.FORBIDDEN);
            }
            filterChain.doFilter(request, response);
        }
        else {
            for(String pattern : URLS_PUBLICAS) {
                if(matcher.match(pattern, request.getServletPath())) {
                    filterChain.doFilter(request, response);
                    return;
                }
            }
            ConfiguraRespostaException.setException(mapper, response, MensagemPersonalizada.ERRO_TOKEN_NAO_INFORMADO, HttpStatus.BAD_REQUEST);
        }
    
    
    
    }

    private UsernamePasswordAuthenticationToken getAutenticacao(String token) {
        
        String username = this.tokenService.getUsuario(token);
        if (username != null) {
            UserDetails user = this.uDService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        }
        return null;
    }



    
}

    
