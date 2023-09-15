package com.minsait.treinamento.dtos.endereco;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
public class EnderecoInsertDTO {
    
    @NotBlank
    @Size(min = 1, max = 50)
    private String Cidade;
    
    @NotBlank
    @Size(max = 50)
    private String Bairro;
    
    @NotBlank
    @Size(max = 500)
    private String Rua;

    @NotNull
    @Positive
    private Integer Numero;

    @NotBlank
    @Pattern(regexp = "\\d{2}\\.?\\d{3}-?\\d{3}", message = "CEP deve estar no formato 00.000-000")
    private String CEP;

    @Size(max = 100)
    private String Referencia;
    

}
