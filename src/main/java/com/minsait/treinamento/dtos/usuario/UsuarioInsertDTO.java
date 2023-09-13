package com.minsait.treinamento.dtos.usuario;

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
public class UsuarioInsertDTO {

    @NotBlank
    @Size(min = 3, max = 300)
    private String nome;
}
