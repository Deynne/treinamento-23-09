package com.minsait.treinamento.model.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Component;

import com.minsait.treinamento.dtos.usuario.UsuarioInsertDTO;

@Component
@Profile({"dev","test"})
public class MockComponent {

    @Autowired
    private UsuarioService us;
    
    @Autowired
    private Environment env;
    
    @PostConstruct
    public void adicionaDadosMock() {
        if(env.acceptsProfiles(Profiles.of("dev"))) {
            us.salvar(UsuarioInsertDTO.builder()
                                    .cpf("11111111111")
                                    .nome("usuario 1")
                                    .rg("111111111")
                                    .rendaAnual(1000.0)
                                    .build());
        }
        else {
            us.salvar(UsuarioInsertDTO.builder()
                    .cpf("22222222222")
                    .nome("usuario 2")
                    .rg("222222222")
                    .rendaAnual(2000.0)
                    .build());
        }
        
    }
}
