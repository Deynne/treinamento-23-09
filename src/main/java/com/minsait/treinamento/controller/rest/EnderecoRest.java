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


import com.minsait.treinamento.dtos.endereco.EnderecoDto;
import com.minsait.treinamento.dtos.endereco.EnderecoInsertDto;
import com.minsait.treinamento.dtos.endereco.EnderecoUpdateDTO;
import com.minsait.treinamento.model.service.EnderecoService;
import com.minsait.treinamento.dtos.endereco.EnderecoDeleteDto;
@RestController
@RequestMapping("endereco")
public class EnderecoRest extends GenericCrudRestImpl<EnderecoService, Long, EnderecoInsertDto, EnderecoUpdateDTO, EnderecoDto> {
    @GetMapping("endereco-por-cep-query-nativa")
    public ResponseEntity<List<EnderecoDto>> achaEnderecoPorCepQueryNativa(@RequestParam @NotBlank @Size(min=3, max=300) String cep) {
        return ResponseEntity.ok(this.service.achaEnderecoPorCepQueryNativa(cep));
    }
}
