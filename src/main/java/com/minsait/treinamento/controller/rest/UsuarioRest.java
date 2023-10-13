package com.minsait.treinamento.controller.rest;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.minsait.treinamento.dtos.Transacao.ExtratoUsuarioDTO;
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
    
    @GetMapping(path = "extrato")
    public ResponseEntity<List<ExtratoUsuarioDTO>> extrato(@NotNull @Positive @RequestParam Long id){
        return ResponseEntity.ok(this.service.extrato(id));
    }
    
    @PutMapping(path = "bloqueia")
    public ResponseEntity<UsuarioDTO> bloqueia(@NotNull @Positive @RequestParam Long id, @NotNull @RequestParam Boolean bloqueio){
        return ResponseEntity.ok(this.service.bloqueio(id, bloqueio, false));
    }
    @PutMapping(path = "altera-estado-bloqueio")
    public ResponseEntity<UsuarioDTO> bloqueio2(@NotNull @Positive @RequestParam Long id, @NotNull @RequestParam Boolean bloqueado){
        return ResponseEntity.ok(this.service.bloqueio(id, bloqueado, false));
    }
    @PutMapping(path = "bloqueia/tudo")
    public ResponseEntity<UsuarioDTO> bloqueiaTudo(@NotNull @Positive @RequestParam Long id, @NotNull @RequestParam Boolean bloqueio){
        return ResponseEntity.ok(this.service.bloqueio(id, bloqueio, true));
    }
}