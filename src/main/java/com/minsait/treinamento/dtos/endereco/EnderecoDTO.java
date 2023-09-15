package com.minsait.treinamento.dtos.endereco;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EnderecoDTO {
    
    private Long id;
    
    private String Cidade;
    
    private String Bairro;
    
    private String Rua;
    
    private Integer Numero;
    
    private String CEP;
    
    private String Referencia;
}
