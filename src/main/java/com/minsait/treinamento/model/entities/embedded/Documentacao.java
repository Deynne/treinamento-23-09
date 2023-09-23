package com.minsait.treinamento.model.entities.embedded;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Documentacao {

    @Column(length = 11, unique = true, nullable = false)
    private String cpf;
    @Column(length = 9, unique = true, nullable = false)
    private String rg;
}
