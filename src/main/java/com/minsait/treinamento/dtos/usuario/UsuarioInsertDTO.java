package com.minsait.treinamento.dtos.usuario;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
    
<<<<<<< HEAD
    @NotBlank
    @Size(min = 9, max = 13)
    private String cpf;
    
    @NotBlank
    @Size(min = 9, max = 13)
    private String rg;

=======
>>>>>>> 38f7860df3f3b7c2323655d889b80db5d908d5ce
    @Positive
    @NotNull
    private Double rendaAnual;
}
