package com.minsait.treinamento.dtos.historicoTransacao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.minsait.treinamento.model.entities.HistoricoTransacao.TipoTransacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoricoTransacaoDTO {
    private Long id;

    private Long idContaPrincipal;

    private Long idContaRelacionada;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    private TipoTransacao tipo;

    private double valor;
}
