package com.minsait.treinamento.dtos.conta;

import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TransacaoSimplesDTO extends DadosContaDTO{
    
    @Positive
    private double valor;
}
