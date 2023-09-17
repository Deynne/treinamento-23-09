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

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "endereco" , uniqueConstraints = {@UniqueConstraint(columnNames = {"cep","numero"})})
public class Endereco extends  GenericEntity<Long> {

	  
    @Column(name = "cidade", length = 50,nullable = false)
    private String cidade;
    @Column(name = "bairro", length = 50,  nullable = false)
    private String bairro;
    @Column(name = "rua", length = 500,  nullable = false)
    private String rua;
    private double numero;
    @Column(name = "cep", length = 9,  nullable = false)
    private String cep;
    @Column(name = "referencia", length = 100,  nullable = true)
    private String referencia;
    
    @JoinColumn(nullable = false, name = "id_usuario")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;
   
}
