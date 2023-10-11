package com.minsait.treinamento.controller.rest;

import com.minsait.treinamento.dtos.conta.DadosContaDTO;
import com.minsait.treinamento.dtos.historicoTransacao.HistoricoTransacaoDTO;
import com.minsait.treinamento.dtos.historicoTransacao.HistoricoTransacaoInsertDTO;
import com.minsait.treinamento.dtos.historicoTransacao.HistoricoTransacaoUpdateDTO;
import com.minsait.treinamento.model.service.HistoricoTransacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("historico-transacoes")
@Validated
public class HistoricoTransacaoRest extends GenericCrudRestImpl<HistoricoTransacaoService, Long, HistoricoTransacaoInsertDTO, HistoricoTransacaoUpdateDTO, HistoricoTransacaoDTO> {

    @GetMapping("extrato-conta")
    public ResponseEntity<List<HistoricoTransacaoDTO>> extrato(@Valid DadosContaDTO dto) {
        return ResponseEntity.ok(this.service.getExtratoConta(dto));

    }

    @GetMapping("extrato-por-periodo")
    public ResponseEntity<List<HistoricoTransacaoDTO>> extratoPorPeriodo(@RequestParam Long id,
                                                                         @RequestParam String endDate) {
        return ResponseEntity.ok(this.service.buscarExtratoPorDataEdpecifica(id, endDate));
    }

    @GetMapping("extrato-por-mes")
    public ResponseEntity<List<HistoricoTransacaoDTO>> extratoPorMes(@RequestParam Long id,
                                                                     @RequestParam Integer quantidadeDeMeses) {
        return ResponseEntity.ok(this.service.buscarExtratoPorMesesAteriores(id, quantidadeDeMeses));
    }

    @GetMapping("extrato-por-dia")
    public ResponseEntity<List<HistoricoTransacaoDTO>> extratoPorDia(@RequestParam Long id,
                                                                     @RequestParam Integer quantidateDeDias) {
        return ResponseEntity.ok(this.service.buscarExtratoPorDiasAteriores(id, quantidateDeDias));
    }

    @GetMapping("historico-extrato-por-codigo")
    public ResponseEntity<List<HistoricoTransacaoDTO>> extratoPorCodigo(@RequestParam UUID codigo) {
        return ResponseEntity.ok(this.service.gerarExtratoPorCodigo(codigo));
    }
}
