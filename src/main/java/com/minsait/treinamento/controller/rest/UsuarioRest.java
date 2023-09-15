package com.minsait.treinamento.controller.rest;

import com.minsait.treinamento.dtos.usuario.UsuarioDTO;
import com.minsait.treinamento.dtos.usuario.UsuarioInsertDTO;
import com.minsait.treinamento.dtos.usuario.UsuarioUpdateDTO;
import com.minsait.treinamento.model.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuario")
public class UsuarioRest extends GenericCrudRestImpl<UsuarioService, Long, UsuarioInsertDTO, UsuarioUpdateDTO, UsuarioDTO> {

    @GetMapping("usuario-{id}")
    public ResponseEntity<UsuarioDTO> acharPorIdDiferente(@PathVariable Long id) {
        return ResponseEntity.<UsuarioDTO>ok(this.service.encontrarPorId(id));
    }
}
