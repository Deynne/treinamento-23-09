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
@Table(name = "Endereco", uniqueConstraints = { @UniqueConstraint(columnNames = {"CEP", "Numero"})})
public class Endereco extends GenericEntity<Long> {
    
    @Column(length = 50, nullable = false)
    private String Cidade;
    
    @Column(length = 50, nullable = false)
    private String Bairro;
    
    @Column(length = 500, nullable = false)
    private String Rua;
    
    @Column(nullable = false)
    private Integer Numero;
    
    @Column(length = 10, nullable = false)
    private String CEP;
    
    @Column(length = 100)
    private String Referencia;
    
    @JoinColumn(nullable = false, name = "id_usuario")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;
    
}
