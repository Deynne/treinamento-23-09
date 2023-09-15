package com.minsait.treinamento.dtos.usuario;

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
public class UsuarioUpdateDTO {

    @NotNull
    @Positive
    private Long id;
    
    @Size(min = 3, max = 300)
    private String nome;

    @Positive
    private Double rendaAnual;
}
