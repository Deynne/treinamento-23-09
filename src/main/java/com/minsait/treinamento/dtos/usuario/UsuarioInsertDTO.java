package com.minsait.treinamento.dtos.usuario;

import javax.validation.constraints.NotBlank;
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
public class UsuarioInsertDTO {

    @NotBlank
    @Size(min = 3, max = 300)
    private String nome;
    
    @Positive
    @NotNull
    private Double rendaAnual;
    
    @NotBlank
    @Size(min = 11,max = 11)
    private String cpf;
    
    @NotBlank
    @Size(min = 9,max = 9)
    private String rg;
    
}
