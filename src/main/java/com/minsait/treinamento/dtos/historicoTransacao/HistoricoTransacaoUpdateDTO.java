package com.minsait.treinamento.dtos.historicoTransacao;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

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
public class HistoricoTransacaoUpdateDTO {
    @NotNull
    @Positive
    private Long id;
    
    @Positive
    private Long idContaPrincipal;

    @Positive
    private Long idContaRelacionada;

    private TipoTransacao tipo;
    
    private Double valor;

}
