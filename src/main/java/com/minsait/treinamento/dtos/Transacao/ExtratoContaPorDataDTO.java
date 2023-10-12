package com.minsait.treinamento.dtos.Transacao;

import java.util.Date;
import java.util.List;
import com.minsait.treinamento.dtos.conta.ContaDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExtratoContaPorDataDTO {
    
    private String verificador;
    
    private Date dataSolicitado;
    
    private Date aPartirDe;
    
    private ContaDTO conta;
    
    private List<ExtratoContaDTO> transacoes;
    
}
