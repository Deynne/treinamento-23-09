package com.minsait.treinamento.controller.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
