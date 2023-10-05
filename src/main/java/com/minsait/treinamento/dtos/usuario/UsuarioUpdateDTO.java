package com.minsait.treinamento.dtos.usuario;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    
    @Pattern(regexp = "^\\d{3}(\\.?\\d{3}){2}-?\\d{2}$", message = "000.000.000-000")
    @Setter(value = AccessLevel.NONE)
    private String cpf;
    
    @Pattern(regexp = "^\\d{1,2}\\.?\\d{3}\\.?\\d{3}-?\\d{1}$", message = "00.000.000-0")
    @Setter(value = AccessLevel.NONE)
    private String rg;

    
    public void setCpf(String value) {
        this.cpf = value.replaceAll("[.-]*", "");
    }
    
    public void setRg(String value) {
        this.rg = value.replaceAll("[.-]*", "");
    }
}
