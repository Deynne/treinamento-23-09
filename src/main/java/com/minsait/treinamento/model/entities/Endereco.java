package com.minsait.treinamento.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name= "endereco", uniqueConstraints = {@UniqueConstraint(columnNames = {"cep","numero"})})
public class Endereco extends GenericEntity<Long> {

    @Column(length = 50, nullable = false)
    private String cidade;
    @Column(length = 50 , nullable = false)
    private String bairro;
    @Column(length = 500  , nullable = false)
    private String rua;
    @Column(length = 10, nullable = false)
    private String numero;
    @Column(length = 10, nullable = false)
    private String cep;
    @Column(length = 100)
    private String referencia;
    
}
