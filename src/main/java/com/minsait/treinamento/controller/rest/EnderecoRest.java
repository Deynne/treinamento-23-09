package com.minsait.treinamento.controller.rest;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minsait.treinamento.dtos.endereco.EnderecoDTO;
import com.minsait.treinamento.dtos.endereco.EnderecoInsertDTO;
import com.minsait.treinamento.dtos.endereco.EnderecoUpdateDTO;
import com.minsait.treinamento.model.service.EnderecoService;

@RestController
@RequestMapping("endereco")
public class EnderecoRest extends GenericCrudRestImpl<EnderecoService, Long, EnderecoInsertDTO, EnderecoUpdateDTO, EnderecoDTO> {
    
    @GetMapping("/{id}")
    public ResponseEntity<List<EnderecoDTO>> acharPorUsuario(@NotNull @Positive @PathVariable Long id) {
        return ResponseEntity.ok(this.service.acharPorUsuarioId(id));
    }

}
