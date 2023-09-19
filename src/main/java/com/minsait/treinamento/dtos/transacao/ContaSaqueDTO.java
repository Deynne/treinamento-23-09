package com.minsait.treinamento.dtos.transacao;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class ContaSaqueDTO {
    
    @NotBlank
    @Size(min = 5, max = 5)
    @Digits(fraction = 0, integer = 5)
    private String agencia;

    @NotBlank
    @Size(min = 7, max = 7)
    @Digits(fraction = 0, integer = 7)
    private String conta;

    @NotNull
    @Positive
    private Double valor;
}

