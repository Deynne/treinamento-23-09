package com.minsait.treinamento.model.entities;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.minsait.treinamento.model.embedded.Documentacao;
import com.minsait.treinamento.model.embedded.InfoFinanceiraUsuario;

import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name= "usuario")
public class Usuario extends GenericEntity<Long>{

    @Setter()
    @Column(nullable = false,length = 300)
    private String nome;
    
    @Embedded
    @Default
    private InfoFinanceiraUsuario infoFinanceira = InfoFinanceiraUsuario.builder().rendaAnual(0.0).build();
    
    @Embedded
    @Default
    private Documentacao documentacao = Documentacao.builder().cpf("INVALID").rg("INVALID").build();
}
