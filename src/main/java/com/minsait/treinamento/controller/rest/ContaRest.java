package com.minsait.treinamento.controller.rest;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.minsait.treinamento.dtos.Transacao.ContaDepositoDTO;
import com.minsait.treinamento.dtos.Transacao.ContaSaqueDTO;
import com.minsait.treinamento.dtos.Transacao.ContaTransferenciaDTO;
import com.minsait.treinamento.dtos.conta.ContaDTO;
import com.minsait.treinamento.dtos.conta.ContaInsertDTO;
import com.minsait.treinamento.dtos.conta.ContaUpdateDTO;
import com.minsait.treinamento.model.service.ContaService;

@RestController
@RequestMapping("conta")
public class ContaRest extends GenericCrudRestImpl<ContaService, Long, ContaInsertDTO, ContaUpdateDTO, ContaDTO> {

    @GetMapping("contas-por-usuario/{id}")
    public ResponseEntity<List<ContaDTO>> acharTodasPorUsuario(@NotNull @Positive @PathVariable Long id) {
        return ResponseEntity.ok(this.service.acharTodasPorUsuario(id));
        
    }
    
    @GetMapping("contas-com-dinheiro-ordem-parametro")
    public ResponseEntity<List<ContaDTO>> acharTodasPorUsuarioOrdemParametro(@NotNull @Positive @RequestParam Long id, 
                                                               @RequestParam @NotNull @PositiveOrZero Double valorMinimo) {
        return ResponseEntity.ok(this.service.acharTodasPorUsuarioEValorMinimoOrdemParametro(id,valorMinimo));
        
    }
    
    @GetMapping("contas-com-dinheiro-nome-parametro")
    public ResponseEntity<List<ContaDTO>> acharTodasPorUsuarioNomeParametro(@NotNull @Positive @RequestParam Long id, 
                                                               @RequestParam @NotNull @PositiveOrZero Double valorMinimo) {
        return ResponseEntity.ok(this.service.acharTodasPorUsuarioEValorNomeParametro(id,valorMinimo));
        
    }
    
    @GetMapping("contas-com-dinheiro-query-nativa")
    public ResponseEntity<List<ContaDTO>> acharTodasPorUsuarioQueryNativa(@NotNull @Positive @RequestParam Long id, 
                                                               @RequestParam @NotNull @PositiveOrZero Double valorMinimo) {
        return ResponseEntity.ok(this.service.acharTodasPorUsuarioEValorMinimoQueryNativa(id,valorMinimo));
    }
    
    @GetMapping("contas-por-nome")
    public ResponseEntity<List<ContaDTO>> achaContasPorNomeUsuario(@RequestParam @NotBlank @Size(min=3, max=300) String nome) {
        return ResponseEntity.ok(this.service.achaContasPorNomeUsuario(nome));
    }
    
    @GetMapping("contas-por-nome-query-nativa")
    public ResponseEntity<List<ContaDTO>> achaContasPorNomeUsuarioQueryNativa(@RequestParam @NotBlank @Size(min=3, max=300) String nome) {
        return ResponseEntity.ok(this.service.achaContasPorNomeUsuarioQueryNativa(nome));
    }
    
    @PutMapping(path = "deposito", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContaDTO> deposita(@Valid @RequestBody ContaDepositoDTO dto) {
        return ResponseEntity.ok(this.service.deposito(dto));
    }
    
    @PutMapping(path = "saque", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContaDTO> deposita(@Valid @RequestBody ContaSaqueDTO dto) {
        return ResponseEntity.ok(this.service.saque(dto));
    }
    
    @PutMapping(path = "transferencia", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContaDTO> deposita(@Valid @RequestBody ContaTransferenciaDTO dto) {
        return ResponseEntity.ok(this.service.transferencia(dto));
    }
}
