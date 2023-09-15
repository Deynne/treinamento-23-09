package com.minsait.treinamento.controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minsait.treinamento.dtos.endereco.EnderecoDTO;
import com.minsait.treinamento.dtos.endereco.EnderecoInsertDTO;
import com.minsait.treinamento.dtos.endereco.EnderecoUpdateDTO;
import com.minsait.treinamento.model.service.EnderecoService;

@RestController
@RequestMapping("endereco")

public class EnderecoRest extends GenericCrudRestImpl<EnderecoService, Long, EnderecoInsertDTO, EnderecoUpdateDTO, EnderecoDTO> {

}
