package com.minsait.treinamento.dtos.usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioInsertDTO {

    @NotBlank
    @Size(min = 3, max = 300)
    private String nome;

    @Positive
    private Double rendaAnual;

    @NotBlank
    @CPF
    private String cpf;

    @NotBlank
    @Size(max = 9)
    private String rg;
}
