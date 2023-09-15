package com.minsait.treinamento.dtos.endereco;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnderecoUpdateDTO {

    @NotNull
    @Positive
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    private String cidade;

    @NotNull
    @Size(min = 3, max = 50)
    private String bairro;

    @NotNull
    @Size(min = 3, max = 50)
    private String rua;

    @NotNull
    private Integer numero;

    @NotNull
    @Size(min = 8, max = 8)
    private String cep;

    private String referencia;
}
