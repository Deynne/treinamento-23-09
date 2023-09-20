package com.minsait.treinamento.dtos.endereco;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnderecoInsertDTO {
    @NotBlank
    @Size(min = 3, max = 50)
    private String cidade;
    @NotBlank
    @Size(min = 3, max = 50)
    private String bairro;
    @NotBlank
    @Size(min = 3, max = 500)
    private String rua;
    @NotBlank
    @Size(min = 1, max = 10)
    private String numero;
    @NotBlank
    @Size(min = 8, max = 9)
    private String cep;
    @NotBlank
    @Size(min = 3, max = 100)
    private String referencia;
    
}
