package com.minsait.treinamento.controller.rest;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.minsait.treinamento.dtos.endereco.EnderecoDTO;
import com.minsait.treinamento.dtos.endereco.EnderecoInsertDTO;
import com.minsait.treinamento.dtos.endereco.EnderecoUpdateDTO;
import com.minsait.treinamento.dtos.endereco.EnderecoDeleteDTO;
import com.minsait.treinamento.model.service.EnderecoService;

@RestController
@RequestMapping("endereco")
public class EnderecoRest extends GenericCrudRestImpl<EnderecoService, Long, EnderecoInsertDTO, EnderecoUpdateDTO, EnderecoDTO> {
    @GetMapping("endereco-por-cep-query-nativa")
    public ResponseEntity<List<EnderecoDTO>> achaEnderecoPorCepQueryNativa(@RequestParam @NotBlank @Size(min=9, max=9) String cep) {
        return ResponseEntity.ok(this.service.achaEnderecoPorCepQueryNativa(cep));
    }
}
