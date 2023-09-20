package com.minsait.treinamento.dtos.endereco;

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
public class EnderecoUpdateDTO {
    @NotNull
    @Positive
    private Long id;
    
    @Size(min = 3, max = 50)
    private String cidade;
    
    @Size(min = 3, max = 50)
    private String bairro;
    
    @Size(min = 3, max = 500)
    private String rua;
    
    @Size(min = 1, max = 10)
    private String numero;
    
    @Size(min = 8, max = 9)
    private String cep;
    
    @Size(min = 3, max = 100)
    private String referencia;

}