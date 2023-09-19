package com.minsait.treinamento.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name="endereco", uniqueConstraints = {@UniqueConstraint(columnNames = {"cep","numero"})})
public class Endereco extends GenericEntity<Long>{

	@Column(nullable = false,length = 50)
	private String cidade; //(50 Caracteres);
	
	@Column(nullable = false,length = 50)
	private String bairro; //(50 Caracteres)
	
	@Column(nullable = false,length = 500)
	private String rua; //(500 Caracteres)
	
	@Column(nullable = false,length = 6)
	private String numero;
	
	@Column(nullable = false,length = 9)
	private String cep;
	
	@Column(nullable = true,length = 100)
	private String referencia; //(100 Caracteres)
	
	@JoinColumn(nullable = false, name = "id_usuario")
	@ManyToOne(fetch = FetchType.LAZY)
	private Usuario usuario;
}
