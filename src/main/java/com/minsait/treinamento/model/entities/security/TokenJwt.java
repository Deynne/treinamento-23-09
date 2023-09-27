package com.minsait.treinamento.model.entities.security;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.minsait.treinamento.model.entities.GenericEntity;
import com.minsait.treinamento.model.entities.Usuario;
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
@Table(name= "token", uniqueConstraints = {@UniqueConstraint(columnNames = {"id_usuario","token"})})
public class TokenJwt extends GenericEntity<Long> {
    @JoinColumn(name = "id_usuario")
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    private Usuario usuario;
    
    @Column(name="data_criacao", nullable = false)
    private Instant dataCriacao;
    
    @Column(name="data_termino", nullable = false)
    private Instant dataTermino;
    
    @Column(length = 3000,nullable = false)
    private String token;
}
