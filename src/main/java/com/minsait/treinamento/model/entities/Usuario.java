package com.minsait.treinamento.model.entities;

import com.minsait.treinamento.model.embedded.InfoFinanceiraUsuario;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "usuario")
public class Usuario extends GenericEntity<Long> {

    @Column(nullable = false, length = 300)
    private String nome;

    @Embedded
    @Builder.Default
    private InfoFinanceiraUsuario infoFinanceira = InfoFinanceiraUsuario.builder().rendaAnual(0.0).build();
}
