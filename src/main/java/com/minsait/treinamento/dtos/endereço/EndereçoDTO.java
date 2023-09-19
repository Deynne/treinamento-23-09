package com.minsait.treinamento.dtos.endereço;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EndereçoDTO {

	private Long id;
	
	private String cidade; //(50 Caracteres);
	
	private String bairro; //(50 Caracteres)
	
	private String rua; //(500 Caracteres)
	
	private String numero;
	
	private String cep;
	
	private String referencia; //(100 Caracteres)
	
	private Long usuarioID;

}
