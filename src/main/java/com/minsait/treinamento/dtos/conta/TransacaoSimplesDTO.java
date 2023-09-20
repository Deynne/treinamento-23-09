package com.minsait.treinamento.dtos.conta;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransacaoSimplesDTO {
    @NotBlank
    @Size(min = 5, max = 5)
    @Digits(fraction = 0, integer = 5)
    private String numAgencia;
    
    @NotBlank
    @Size(min = 7, max = 7)
    @Digits(fraction = 0, integer = 7)
    private String numConta;
    
    @Positive
    private double valor;
}
