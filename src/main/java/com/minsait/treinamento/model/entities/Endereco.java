package com.minsait.treinamento.model.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;


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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Usuario usuario;
}
