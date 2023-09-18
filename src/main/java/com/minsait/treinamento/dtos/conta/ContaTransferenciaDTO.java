package com.minsait.treinamento.dtos.conta;

import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class ContaTransferenciaDTO {

    @Size(min = 7, max = 7)
    @Digits(fraction = 0, integer = 7)
    private String contaOrigem;

    @Size(min = 5, max = 5)
    @Digits(fraction = 0, integer = 5)
    private String agenciaOrigem;

    @Size(min = 7, max = 7)
    @Digits(fraction = 0, integer = 7)
    private String contaDestino;

    @Size(min = 5, max = 5)
    @Digits(fraction = 0, integer = 5)
    private String agenciaDestino;

    @CPF
    private String cpf;

    @Positive
    private double saldo;
}
