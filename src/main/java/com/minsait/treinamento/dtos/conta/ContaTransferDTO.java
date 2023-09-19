package com.minsait.treinamento.dtos.conta;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContaTransferDTO {
	
    @Size(min = 5, max = 5)
    @Digits(fraction = 0, integer = 5)
    private String numAgenciaOrigem;
    
    @Size(min = 7, max = 7)
    @Digits(fraction = 0, integer = 7)
    private String numContaOrigem;
	
    @Size(min = 5, max = 5)
    @Digits(fraction = 0, integer = 5)
    private String numAgenciaDestino;
    
    @Size(min = 7, max = 7)
    @Digits(fraction = 0, integer = 7)
    private String numContaDestino;
	
    
    private String cpf;
    
    @NotNull
    @Positive
    private double valor;
}
