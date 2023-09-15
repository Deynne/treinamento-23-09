package com.minsait.treinamento.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Table(name = "endereco", uniqueConstraints = {@UniqueConstraint(columnNames = {"cep", "numero"})})
public class Endereco extends GenericEntity<Long> {

    @Column(length = 50, nullable = false)
    private String cidade;

    @Column(length = 50, nullable = false)
    private String bairro;

    @Column(length = 500, nullable = false)
    private String rua;

    @Column(length = 20, nullable = false)
    private String numero;

    @Column(length = 9, nullable = false)
    private String cep;

    @Column(length = 100)
    private String referencia;
}
