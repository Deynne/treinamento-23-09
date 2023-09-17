package com.minsait.treinamento.model.entities;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;

import com.minsait.treinamento.model.embedded.Documentacao;

import lombok.AllArgsConstructor;
import lombok.Builder.Default;
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
public class Usuario extends GenericEntity<Long> {

    @Column(nullable = false,length = 300)
    private String nome;
    
    @Embedded
    @Default
    private Documentacao documentacao = Documentacao.builder().cpf("").rg("").build();
}
