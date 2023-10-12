package com.minsait.treinamento.controller.rest;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.minsait.treinamento.dtos.Transacao.ExtratoContaPorDataDTO;
import com.minsait.treinamento.model.service.ExtratoService;

@RestController
@RequestMapping("extrato")
public class ExtratoRest {
    @Autowired
    private ExtratoService service;
    
    @GetMapping(path = "buscarPorContaEData")
    public ResponseEntity<ExtratoContaPorDataDTO> extratoPorContaEData(@NotNull @Positive @RequestParam Long id, @NotNull @Past Date aPartirDe){
        return ResponseEntity.ok(this.service.extratoPorData(id, aPartirDe));
    }
    
    @GetMapping(path = "buscarPorVerificador")
    public ResponseEntity<ExtratoContaPorDataDTO> extratoPorVerificador(@NotBlank String verificador){
        return ResponseEntity.ok(this.service.extratoPorVerificador(verificador));
    }
    
    @GetMapping(path = "validar")
    public ResponseEntity<Boolean> validaExtrato(@NotBlank @RequestParam String uuid){
        return ResponseEntity.ok(this.service.validaExtrato(uuid));
    }
    
}
