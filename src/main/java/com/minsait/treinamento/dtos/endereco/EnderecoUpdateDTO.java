package com.minsait.treinamento.dtos.endereco;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EnderecoUpdateDTO {
    
    @NotNull
    @Positive
    private Long id;
    
    @Length(max = 50)
    private String Cidade;
    
    @Length(max = 50)
    private String Bairro;
    
    @Length(max = 500)
    private String Rua;

    @Positive
    private Integer Numero;

    @Pattern(regexp = "\\d{2}\\.?\\d{3}-?\\d{3}", message = "00.000-000")
    @Setter(value = AccessLevel.NONE)
    private String CEP;

    @Length(max = 100)
    private String Referencia;
    
    @Positive
    private Long UsuarioId;
    

    public void setCEP(String value) {
        this.CEP = value.replaceAll("[.-]*", "");
    }
}
