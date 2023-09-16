package com.minsait.treinamento.dtos;

import com.minsait.treinamento.dtos.conta.ContaDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IdentificadorBasicoDTO<I extends Number> {

    private I id;
    
    private String nome;
}
