package com.minsait.treinamento.controller.rest;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.minsait.treinamento.dtos.extrato.ExtratoDTO;
import com.minsait.treinamento.dtos.extrato.ExtratoInsertDTO;
import com.minsait.treinamento.dtos.extrato.ExtratoUpdateDTO;
import com.minsait.treinamento.model.entities.Extrato;
import com.minsait.treinamento.model.service.ExtratoService;

@RestController
@RequestMapping("extrato")
@Validated
public class ExtratoRest extends GenericCrudRestImpl<ExtratoService, Long, ExtratoInsertDTO, ExtratoUpdateDTO, ExtratoDTO>{
	
	@GetMapping("extrato-por-uuid")
	public ResponseEntity<Extrato>encontrarExtratoPorUuid(@RequestParam UUID identificador){
		return ResponseEntity.ok(this.service.encontrarPorUUid(identificador));
		
	}
	
	@GetMapping("extrato-por-id")
	public ResponseEntity<ExtratoDTO>encontrarExtratoPorId(@RequestParam Long id){
		return ResponseEntity.ok(this.service.encontrarPorId(id));
		
	}

}
