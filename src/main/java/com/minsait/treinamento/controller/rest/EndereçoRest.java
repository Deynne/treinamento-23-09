package com.minsait.treinamento.controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minsait.treinamento.dtos.endereço.EndereçoDTO;
import com.minsait.treinamento.dtos.endereço.EndereçoInsertDTO;
import com.minsait.treinamento.dtos.endereço.EndereçoUpdateDTO;
import com.minsait.treinamento.model.service.EndereçoService;

@RestController
@RequestMapping("endereco")
public class EndereçoRest extends GenericCrudRestImpl<EndereçoService, Long, EndereçoInsertDTO, EndereçoUpdateDTO, EndereçoDTO> {
	
}
