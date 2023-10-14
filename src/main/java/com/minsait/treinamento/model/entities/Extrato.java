package com.minsait.treinamento.model.entities;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "extratos")
public class Extrato extends GenericEntity<Long> {
		
	@JoinColumn(name = "conta_vinculada", nullable=false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Conta contaVinculada;
	
	@JoinColumn(name = "historico_transacoes")
	@ManyToOne(fetch = FetchType.LAZY)
	private HistoricoTransacao transacoes;
                    
	@Column(name="dataInicio")
    private LocalDateTime dataInicio;
		
	@Column(name="dataFim")
    private LocalDateTime dataSolicitacao;
		
	@Column(name="identificadorExtrato")
	protected UUID identificador;
	
		
}
    

