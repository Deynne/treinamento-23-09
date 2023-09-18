package com.minsait.treinamento.dtos.Transacao;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
public class ContaTransferenciaDTO {
    
    @NotBlank
    @Size(min = 5, max = 5)
    @Digits(fraction = 0, integer = 5)
    private String agenciaOrigem;

    @NotBlank
    @Size(min = 7, max = 7)
    @Digits(fraction = 0, integer = 7)
    private String contaOrigem;

    @NotBlank
    @Size(min = 5, max = 5)
    @Digits(fraction = 0, integer = 5)
    private String agenciaDestino;

    @NotBlank
    @Size(min = 7, max = 7)
    @Digits(fraction = 0, integer = 7)
    private String contaDestino;
    
    @NotBlank
    @Pattern(regexp = "^\\d{3}(\\.?\\d{3}){2}-?\\d{2}$", message = "000.000.000-000")
    private String cpf;
    
    @NotNull
    @Positive
    private Double valor;
    
    
   /*
    * Agencia de origem
    * Conta de origem
    * Agencia de destino
    * Conta de conta de destino
    * cpf do usuário (quando necessário)
   * Valor a ser transferido.
    Se o CPF informado não corresponder ao usuário de destino, a operação deve ser rejeitada
    Não deve ser possível ficar com saldo negativo ao fim da operação
    A combinação agencia-conta de origem e destino não devem ser iguais.
    O Valor a ser transferido não deve ser negativo ou zero.
    Qualquer problema durante a transação deve implicar em um cancelamento de todos os processos realizados
    
    */
}
