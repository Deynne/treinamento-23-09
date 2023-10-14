package com.minsait.treinamento.dtos.extrato;

import java.time.LocalDateTime;
import java.util.UUID;

import com.minsait.treinamento.model.entities.Conta;
import com.minsait.treinamento.model.entities.HistoricoTransacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExtratoDTO {
	
	private Long id;
	
	private Conta contaVinculada;
	
	private HistoricoTransacao transacoes;
    	
    private LocalDateTime dataInicio;
		
    private LocalDateTime dataSolicitacao;
	
	protected UUID identificador;

}
