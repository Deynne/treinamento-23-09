package com.minsait.treinamento.model.embedded;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfoFinanceiraUsuario {

    @Column(name = "renda_anual")
    private double rendaAnual;
}
