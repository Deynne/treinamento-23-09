package com.minsait.treinamento.dtos.historicoTransacao;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.minsait.treinamento.model.entities.HistoricoTransacao.TipoTransacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoricoTransacaoInsertDTO {
    @NotNull
    @Positive
    private Long idContaPrincipal;
    
    @Positive
    private Long idContaRelacionada;

    @NotNull
    private TipoTransacao tipo;

    private double valor;
}
