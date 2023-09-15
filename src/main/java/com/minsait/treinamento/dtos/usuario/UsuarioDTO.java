package com.minsait.treinamento.dtos.usuario;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {
    
    private Long id;
    
    private String nome;
    
    private double rendaAnual;
    
    private String cpf;
    
    private String rg;

}
