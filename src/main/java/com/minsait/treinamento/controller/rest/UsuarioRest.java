package com.minsait.treinamento.controller.rest;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.minsait.treinamento.dtos.usuario.UsuarioDTO;
import com.minsait.treinamento.dtos.usuario.UsuarioInsertDTO;
import com.minsait.treinamento.dtos.usuario.UsuarioUpdateDTO;
import com.minsait.treinamento.model.service.UsuarioService;

@RestController
@RequestMapping("usuario")
public class UsuarioRest extends GenericCrudRestImpl<UsuarioService, Long, UsuarioInsertDTO, UsuarioUpdateDTO, UsuarioDTO> {

    @GetMapping("usuario-{id}")
    public ResponseEntity<UsuarioDTO> acharPorIdDiferente(@PathVariable Long id) {
        return ResponseEntity.<UsuarioDTO>ok(this.service.encontrarPorId(id));
    }
    
    @PutMapping("altera-estado-bloqueio")
    public ResponseEntity<Boolean> alteraEstadoBloqueio(@RequestParam @Positive @NotNull Long id, boolean bloqueado) {
        return ResponseEntity.<Boolean>ok(this.service.alterarEstadoBloqueio(id, bloqueado));
    }
    
    @PutMapping("altera-estado-bloqueio-cascata")
    public ResponseEntity<Boolean> alteraEstadoBloqueioCascata(@RequestParam @Positive @NotNull Long id, boolean bloqueado) {
        return ResponseEntity.<Boolean>ok(this.service.alterarEstadoBloqueioCascata(id, bloqueado));
    }
}
