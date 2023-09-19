package com.minsait.treinamento.dtos.conta;

import com.minsait.treinamento.dtos.IdentificadorBasicoDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContaDTO {
    private Long id;
    private String numAgencia;
    private String numConta;
    private double saldo;
    private Long idUsuario;
    private Boolean bloqueado;
    
//    private IdentificadorBasicoDTO<Long> usuario;
}
