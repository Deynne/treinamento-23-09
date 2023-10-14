package com.minsait.treinamento.controller.rest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.minsait.treinamento.dtos.conta.DadosContaDTO;
import com.minsait.treinamento.dtos.historicoTransacao.HistoricoTransacaoDTO;
import com.minsait.treinamento.dtos.historicoTransacao.HistoricoTransacaoInsertDTO;
import com.minsait.treinamento.dtos.historicoTransacao.HistoricoTransacaoUpdateDTO;
import com.minsait.treinamento.model.service.HistoricoTransacaoService;

@RestController
@RequestMapping("historico-transacoes")
@Validated
public class HistoricoTransacaoRest extends GenericCrudRestImpl<HistoricoTransacaoService, Long, HistoricoTransacaoInsertDTO, HistoricoTransacaoUpdateDTO, HistoricoTransacaoDTO> {

    @GetMapping("extrato-conta")
    public ResponseEntity<List<HistoricoTransacaoDTO>> extrato(@Valid DadosContaDTO dto) {
        return ResponseEntity.ok(this.service.getExtratoConta(dto));   
    }
    
    @GetMapping("extrato-conta-periodo")
    public ResponseEntity<List<HistoricoTransacaoDTO>> extratoPeriodo(@Valid DadosContaDTO dto, @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) LocalDateTime timestamp) {
        return ResponseEntity.ok(this.service.getExtratoContaPeriodo(dto, timestamp));   
    }
}
