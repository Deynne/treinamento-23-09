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
public class TransferenciaDTO {
    @NotBlank
    @Size(min = 5, max = 5)
    @Digits(fraction = 0, integer = 5)
    private String numAgenciaOrigem;
    
    @NotBlank
    @Size(min = 7, max = 7)
    @Digits(fraction = 0, integer = 7)
    private String numContaOrigem;
    
    @Size(min = 5, max = 5)
    @Digits(fraction = 0, integer = 5)
    private String numAgenciaDestino;
    @Size(min = 7, max = 7)
    @Digits(fraction = 0, integer = 7)
    private String numContaDestino;
    
    @Size(min = 11, max = 11)
    private String cpf;
    
    @Positive
    private double valor;
}
