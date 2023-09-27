package com.minsait.treinamento.dtos.security;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CredenciaisDTO {
    @NotBlank
    private String usuario;
    @NotBlank
    private String senha;

}
