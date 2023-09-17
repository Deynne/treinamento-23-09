package com.minsait.treinamento.model.embedded;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Documentacao {

    @CPF
    @Column(length = 11, nullable = false, unique = true)
    private String cpf;

    @Column(length = 9, nullable = false, unique = true)
    private String rg;
}
