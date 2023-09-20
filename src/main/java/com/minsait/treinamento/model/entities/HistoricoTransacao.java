package com.minsait.treinamento.model.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
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
@Table(name = "historico_transacao")
public class HistoricoTransacao extends GenericEntity<Long> {

    @JoinColumn(name = "id_conta_principal",nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Conta contaPrincipal;
    
    @JoinColumn(name = "id_conta_relacionada")
    @ManyToOne(fetch = FetchType.LAZY)
    private Conta contaRelacionada;
    
    private LocalDateTime timestamp;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTransacao tipo;
    
    private double valor;
    
    @Getter
    @AllArgsConstructor
    public static enum TipoTransacao {
        DEPOSITO("Depósito"),
        SAQUE("Saque"),
        TRANSFERENCIA("Transferência");
        
        private String descricao;
    }
    
}
