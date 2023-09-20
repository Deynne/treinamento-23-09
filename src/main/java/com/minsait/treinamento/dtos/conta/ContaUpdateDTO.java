package com.minsait.treinamento.dtos.conta;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContaUpdateDTO {
    @NotNull
    @Positive
    private Long id;
    @Size(min = 5, max = 5)
    @Digits(fraction = 0, integer = 5)
    private String numAgencia;
    @Size(min = 7, max = 7)
    @Digits(fraction = 0, integer = 7)
    private String numConta;
    @PositiveOrZero
    private Double saldo;
    @Positive
    private Long idUsuario;
    
    private Boolean bloqueado;
}
