package com.minsait.treinamento.dtos.conta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContaDepositarSacarDTO {

    @Size(min = 5, max = 5)
    @Digits(fraction = 0, integer = 5)
    private String numAgencia;

    @Size(min = 7, max = 7)
    @Digits(fraction = 0, integer = 7)
    private String numConta;

    @Positive
    private double saldo;
}
