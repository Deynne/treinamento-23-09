package com.minsait.treinamento.model.embedded;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Documentacao {
	
	@Column(nullable = false,unique = true,length = 11)
	private String cpf; //(11 caracteres)
	
	@Column(nullable = false,unique = true,length = 9)
	private String rg; //(9 Caracteres)

}
