package com.minsait.treinamento.dtos.endereco;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnderecoUpdateDTO {

	 @NotNull
	    @Positive
	    private Long id;
		@Size(min = 5, max = 50)
	    private String cidade;
	    @Size(min = 5, max = 50)
	    private String bairro;
	    @Size(min = 5, max = 500)
	    private String rua;
	    @Size(min = 9, max = 9)
	    private String cep;
	    
	    @Size(min = 5, max = 100)
	    private String referencia;
	    @NotNull
	    @Positive
	    private double numero;
	    @NotNull
	    @Positive
	    private Long idUsuario;
	    
	    
}
