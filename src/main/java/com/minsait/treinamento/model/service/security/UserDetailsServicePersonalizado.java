package com.minsait.treinamento.model.service.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.minsait.treinamento.dtos.usuario.UsuarioInsertDTO;
import com.minsait.treinamento.model.entities.Usuario;
import com.minsait.treinamento.model.entities.enumerators.security.CargosSistema;
import com.minsait.treinamento.model.security.UsuarioAutenticador;
import com.minsait.treinamento.model.service.UsuarioService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@ConditionalOnProperty(name = "security-ativo",prefix = "app.security", matchIfMissing = true)
public class UserDetailsServicePersonalizado implements UserDetailsService, UserDetailsPasswordService {

    private final UsuarioService usuarioService;
    
    public UserDetailsServicePersonalizado(@Value("${spring.security.user.name}")
                                           String usuario,
                                           @Value("${spring.security.user.password}")
                                            String senha,
                                            @Value("${spring.security.user.roles}")
                                            List<String> cargos,
                                            @Autowired
                                            Environment env,
                                            @Autowired
                                            PasswordEncoder pe,
                                            @Autowired
                                            UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        if(!env.acceptsProfiles(Profiles.of("prod"))) {
            UserDetails ud = User.builder()
                                    .accountExpired(false)
                                    .accountLocked(false)
                                    .roles(cargos.toArray(new String[0]))
                                    .credentialsExpired(false)
                                    .disabled(false)
                                    .password(senha)
                                    .passwordEncoder((password) -> pe.encode(password))
                                    .username(usuario)
                                    .build();
            try {
                this.usuarioService.encontrarEntidadePorUsuario(usuario);
            } catch(Exception e) {
                try {
                    this.usuarioService.salvar(UsuarioInsertDTO.builder()
                                                                    .cargos(CargosSistema.getCargoSistema(ud.getAuthorities()))
                                                                    .cpf("00000000000")
                                                                    .rg("000000000")
                                                                    .nome("Administrador Dev")
                                                                    .usuario(ud.getUsername())
                                                                    .senha(senha)
                                                                    .rendaAnual(0.01)
                                                                    .build());
                }
                catch(Exception e1) {
                    log.error(e1.getMessage(), e1);
                }
            }
        }
        else {
            try {
                this.usuarioService.excluir(usuario).getId();
            } catch(Exception e) {
                // Não precisa fazer nada caso não consiga
            }
        }
    }
    
    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        Usuario u = this.usuarioService.encontrarEntidadePorUsuario(usuario);
        
        return new UsuarioAutenticador<Long>(u.getId(), usuario , u.getSenha(), u.getCargos());
    }
    
    @Override
    public UserDetails updatePassword(UserDetails usuario, String senhaNova) {
        Usuario u = usuarioService.atualizarSenha(usuario.getUsername(),senhaNova);
        
        return new UsuarioAutenticador<Number>(u.getId(),usuario.getUsername(), senhaNova, usuario.getAuthorities());
    }

}
