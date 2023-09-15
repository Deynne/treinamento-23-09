

package com.minsait.treinamento.model.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


import javax.persistence.*;

@Entity
@Table(name = "enderecos", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"cep", "numero"})})
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Endereco extends GenericEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String cidade;

    @Column(length = 50, nullable = false)
    private String bairro;

    @Column(length = 500, nullable = false)
    private String rua;

    @Column(length = 10, nullable = false)
    private Integer numero;

    @Column(length = 8, nullable = false)
    private String cep;

    @Column(length = 100)
    private String referencia;

}
