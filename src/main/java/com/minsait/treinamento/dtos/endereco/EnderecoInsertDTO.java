package com.minsait.treinamento.dtos.endereco;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnderecoInsertDTO {

    @Size(min = 3, max = 50)
    private String cidade;

    @Size(min = 3, max = 50)
    private String bairro;

    @Size(min = 3, max = 50)
    private String rua;

    private Integer numero;

    @Size(min = 8, max = 8)
    private String cep;

    private String referencia;
}
