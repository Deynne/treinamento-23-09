package com.minsait.treinamento.dtos.endereço;

import javax.validation.constraints.NotBlank;
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
public class EndereçoUpdateDTO {

    @NotNull
    @Positive
    private Long id;
    
	@NotBlank
    @Size(min = 3, max = 50)
	private String cidade; //(50 Caracteres);
	
	@NotBlank
    @Size(min = 3, max = 50)
	private String bairro; //(50 Caracteres)
	
	@NotBlank
    @Size(min = 3, max = 500)
	private String rua; //(500 Caracteres)
	
	@NotBlank
	private int numero;
	
	@NotBlank
	private int cep;
	
    @Size(min = 3, max = 100)
	private String referencia; //(100 Caracteres)

}
