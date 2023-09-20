package com.minsait.treinamento.dtos.historicoTransacao;

import java.time.LocalDateTime;

import com.minsait.treinamento.dtos.conta.ContaDTO;
import com.minsait.treinamento.model.entities.HistoricoTransacao.TipoTransacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoricoTransacaoDTO {
    private Long id;
    
    private Long idContaPrincipal;
    
    private Long idContaRelacionada;
    
    private LocalDateTime timestamp;
    
    private TipoTransacao tipo;
    
    private double valor;
}
