package com.minsait.treinamento.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class IdentificadorBasicoDTO<I extends Number> {

    private I id;
    
    private String nome;
}
