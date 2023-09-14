package com.minsait.treinamento.model.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name= "conta" ,uniqueConstraints = {@UniqueConstraint(columnNames = {"num_agencia", "num_conta"})})
public class Conta extends GenericEntity<Long>{

    @Column(name = "num_agencia", length = 5, nullable = false)
    private String numAgencia;

    @Column(name = "num_conta", length = 7, nullable = false)
    private String numConta;

    private double saldo;

    @JoinColumn(nullable = false, name = "id_usuario")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;
}
