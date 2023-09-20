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
@Table(name = "conta" , uniqueConstraints = {@UniqueConstraint(columnNames = {"num_agencia","num_conta"})})
public class Conta  extends GenericEntity<Long> {
    
    @Column(name = "num_agencia", length = 5,nullable = false)
    private String numAgencia;
    @Column(name = "num_conta", length = 7,  nullable = false)
    private String numConta;
   
    private double saldo;
    
    @JoinColumn(nullable = false, name = "id_usuario")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;
    
    private boolean bloqueado;

}
