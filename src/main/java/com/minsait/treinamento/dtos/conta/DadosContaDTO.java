package com.minsait.treinamento.dtos.conta;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class DadosContaDTO {

    @NotBlank
    @Size(min = 5, max = 5)
    @Digits(fraction = 0, integer = 5)
    private String numAgencia;
    
    @NotBlank
    @Size(min = 7, max = 7)
    @Digits(fraction = 0, integer = 7)
    private String numConta;
}
