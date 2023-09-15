package com.minsait.treinamento.dtos.endereco;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class EnderecoUpdateDTO {

    @NotNull
    @Positive
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String cidade;

    @NotBlank
    @Size(max = 50)
    private String bairro;

    @NotBlank
    @Size(max = 500)
    private String rua;

    @NotBlank
    @Size(max = 20)
    private String numero;

    @NotBlank
    @Size(min = 8, max = 9)
    private String cep;

    @Size(max = 100)
    private String referencia;
}
