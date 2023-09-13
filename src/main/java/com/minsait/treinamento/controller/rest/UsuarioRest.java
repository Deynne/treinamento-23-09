package com.minsait.treinamento.controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minsait.treinamento.dtos.usuario.UsuarioDTO;
import com.minsait.treinamento.dtos.usuario.UsuarioInsertDTO;
import com.minsait.treinamento.dtos.usuario.UsuarioUpdateDTO;
import com.minsait.treinamento.model.service.UsuarioService;

@RestController
@RequestMapping("usuario")
public class UsuarioRest extends GenericCrudRestImpl<UsuarioService, Long, UsuarioInsertDTO, UsuarioUpdateDTO, UsuarioDTO> {

}
