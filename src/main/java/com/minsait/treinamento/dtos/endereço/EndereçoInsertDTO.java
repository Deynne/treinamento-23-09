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
public class EndereçoInsertDTO {
	
	@NotBlank
    @Size(min = 3, max = 50)
	private String cidade; //(50 Caracteres);
	
	@NotBlank
    @Size(min = 3, max = 50)
	private String bairro; //(50 Caracteres)
	
	@NotBlank
    @Size(min = 3, max = 50)
	private String rua; //(500 Caracteres)
	
	@NotNull
	@Positive
	private String numero;
	
	@NotBlank
	private String cep;
	
	private String referencia; //(100 Caracteres)
    
    @NotNull
    @Positive
	private Long usuarioID;
	
}
