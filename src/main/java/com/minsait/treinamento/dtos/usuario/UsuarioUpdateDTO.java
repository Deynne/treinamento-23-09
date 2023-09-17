package com.minsait.treinamento.dtos.usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

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

    @NotBlank
    @CPF
    private String cpf;

    @NotBlank
    @Size(min = 9, max = 9)
    private String rg;
}
