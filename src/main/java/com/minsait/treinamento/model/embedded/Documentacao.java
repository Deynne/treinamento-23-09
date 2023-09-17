package com.minsait.treinamento.model.embedded;

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
    
    @Column(length = 11, nullable = false, unique = true)
    private String cpf;
    
    @Column(length = 9, nullable = false, unique = true)
    private String rg;

}