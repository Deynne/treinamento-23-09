package com.minsait.treinamento.dtos.Transacao;

import java.util.Date;
import java.util.Optional;

import com.minsait.treinamento.model.entities.Conta;
import com.minsait.treinamento.model.enumerators.TipoOperacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExtratoContaDTO {
    
    private Date data;
    private TipoOperacao operacao;
    private Double valor;
    private String contaOp;
    private String agenciaOp;
    
}
