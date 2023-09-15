package com.minsait.treinamento.dtos.endereco;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnderecoDTO {

    private Long id;
    private String cidade;
    private String bairro;
    private String rua;
    private String numero;
    private String cep;
    private String referencia;
}
