package com.minsait.treinamento.controller.rest;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minsait.treinamento.dtos.security.CredenciaisDTO;
import com.minsait.treinamento.exceptions.GenericException;
import com.minsait.treinamento.exceptions.MensagemPersonalizada;

@RestController
@RequestMapping("auth")
public class AutenticacaoRest {

    @PostMapping("login")
    public void login (@Valid @RequestBody CredenciaisDTO dto) {
        // Este método não será executado, mas precisa ser criado para que o swagger reconheça o endpoint de login
        throw new GenericException(MensagemPersonalizada.ERRO_INESPERADO);
    }
    @PostMapping("logout")
    public void logout () {
        // Este método não será executado, mas precisa ser criado para que o swagger reconheça o endpoint de login
        throw new GenericException(MensagemPersonalizada.ERRO_INESPERADO);
    }
}
