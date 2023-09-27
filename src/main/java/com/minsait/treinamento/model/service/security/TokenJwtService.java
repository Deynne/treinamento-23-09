package com.minsait.treinamento.model.service.security;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.minsait.treinamento.dtos.security.token.JWTDataDTO;
import com.minsait.treinamento.dtos.security.token.TokenJwtDTO;
import com.minsait.treinamento.dtos.security.token.TokenJwtInsertDTO;
import com.minsait.treinamento.dtos.security.token.TokenJwtUpdateDTO;
import com.minsait.treinamento.exceptions.GenericException;
import com.minsait.treinamento.exceptions.MensagemPersonalizada;
import com.minsait.treinamento.model.entities.Usuario;
import com.minsait.treinamento.model.entities.security.TokenJwt;
import com.minsait.treinamento.model.repositories.security.TokenJwtRepository;
import com.minsait.treinamento.model.service.GenericCrudServiceImpl;
import com.minsait.treinamento.model.service.UsuarioService;
import com.minsait.treinamento.utils.security.JWTUtil;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TokenJwtService extends GenericCrudServiceImpl<TokenJwtRepository, Long, TokenJwtInsertDTO, TokenJwtUpdateDTO, TokenJwtDTO> {

    @Autowired
    private UsuarioService usuarioService;
    
    
    private final JWTDataDTO JWT_DATA;
    
    public TokenJwtService(@Value("${app.security.jwt.secret}") 
                            String secret,
                           @Value("${app.security.jwt.expiration}")
                            Long expiration) {
        
        this.JWT_DATA = JWTDataDTO.builder()
                                    .secret(secret)
                                    .expiration(expiration)
                                    .build();
        
    }
    
    private final long DELAYTIME = 43200000L;
    
    @Override
    public TokenJwtDTO salvar(@Valid TokenJwtInsertDTO dto) {
        Usuario u = this.usuarioService.encontrarEntidadePorId(dto.getIdUsuario());
        TokenJwt t = TokenJwt.builder()
                             .dataCriacao(dto.getDataCriacao())
                             .dataTermino(dto.getDataTermino())
                             .usuario(u)
                             .token(dto.getToken())
                             .build();
        
        this.repository.save(t);
        
        return toDTO(t);
    }

    public static TokenJwtDTO toDTO(TokenJwt t) {        
        return TokenJwtDTO.builder()
                            .dataCriacao(t.getDataCriacao())
                            .dataTermino(t.getDataTermino())
                            .idUsuario(t.getUsuario().getId())
                            .token(t.getToken())
                            .build();
    }

    @Override
    public TokenJwtDTO atualizar(@Valid TokenJwtUpdateDTO dto) {
        TokenJwt t = this.repository.findById(dto.getIdUsuario())
                    .orElseThrow(() -> new GenericException(MensagemPersonalizada.ALERTA_ELEMENTO_NAO_ENCONTRADO,
                                        TokenJwt.class.getSimpleName()));

        if(dto.getDataCriacao() != null) {
            t.setDataCriacao(dto.getDataCriacao());
        }
        
        if(dto.getDataTermino() != null) {
            t.setDataTermino(dto.getDataTermino());
        }
        
        if(dto.getIdUsuario() != null) {
            Usuario u = this.usuarioService.encontrarEntidadePorId(dto.getIdUsuario());
            t.setUsuario(u);
        }
        
        if(dto.getToken() != null) {
            t.setToken(dto.getToken());
        }
        
        this.repository.save(t);
        
        return toDTO(t);
    }

    @Override
    public TokenJwtDTO excluir(@NotNull @Positive Long id) {
        TokenJwt t = this.repository.findById(id)
                .orElseThrow(() -> new GenericException(MensagemPersonalizada.ALERTA_ELEMENTO_NAO_ENCONTRADO,
                                    TokenJwt.class.getSimpleName()));
        return this.excluir(t);
    }

    private TokenJwtDTO excluir(TokenJwt t) {
        this.repository.delete(t);
        
        return toDTO(t);
    }

    @Override
    public TokenJwtDTO encontrarPorId(@NotNull @Positive Long id) {
        return toDTO(this.repository.findById(id)
                .orElseThrow(() -> new GenericException(MensagemPersonalizada.ALERTA_ELEMENTO_NAO_ENCONTRADO,
                                    TokenJwt.class.getSimpleName())));
    }

    @Override
    public List<TokenJwtDTO> encontrarTodos() {
        return this.repository.findAll().stream().map(TokenJwtService::toDTO).collect(Collectors.toList());
    }
    
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
    public String trataToken(String usuario, Collection<? extends GrantedAuthority> cargos) {
        TokenJwt t = this.repository.findByUsuario(usuario);
        
        if(t == null)
            return this.criaToken(usuario, cargos);
        else
            return this.atualizaToken(t, cargos);
    }

    private String criaToken(String usuario, Collection<? extends GrantedAuthority> cargos) {
        Usuario u = this.usuarioService.encontrarEntidadePorUsuario(usuario);
        
        Instant agora = Instant.now();
        
        Instant fim = agora.plusMillis(JWT_DATA.getExpiration());

        return this.salvar(TokenJwtInsertDTO.builder()
                                            .idUsuario(u.getId())
                                            .dataCriacao(agora)
                                            .dataTermino(fim)
                                            .token(JWTUtil.generateToken(JWT_DATA, usuario,cargos, null))
                                            .build()).getToken();
    }

    private String atualizaToken(TokenJwt token,Collection<? extends GrantedAuthority> cargos) {
        Instant agora = Instant.now();
        
        Instant fim = agora.plusMillis(JWT_DATA.getExpiration());
        
        token.setDataCriacao(agora);
        
        token.setDataTermino(fim);  
        
        token.setToken(JWTUtil.generateToken(JWT_DATA, token.getUsuario().getUsuario(),cargos, null));
        
        token = this.repository.save(token);
        return token.getToken();
    }
    
    @Scheduled(fixedRate = DELAYTIME)
    @Transactional(rollbackFor = Exception.class)
    public void eliminaTokensInvalidos() {
        
        List<TokenJwt> tokens = this.repository.findAllExpired(Instant.now());
        
        if(tokens != null && !tokens.isEmpty()) {
            log.info("Removendo tokens invalidos");
            this.repository.deleteAll(tokens);
        }
    }
    
    public String getUsuario(String token) {
        TokenJwt t = this.repository.findByToken(token);
        if(t == null) {
            return null;
        }
        try {
            return JWTUtil.getUsername(token, JWT_DATA.getSecret()); 
        }
        catch(JwtException e) {
            this.repository.delete(t);
            return null;
        }
    }

    public void remover(String token) {
        if(!JWTUtil.checkToken(token, JWT_DATA.getSecret()))
            throw new GenericException(MensagemPersonalizada.ERRO_TOKEN_INVALIDO);
        TokenJwt t = this.repository.findByToken(token);
        this.excluir(t);
        
    }
}
