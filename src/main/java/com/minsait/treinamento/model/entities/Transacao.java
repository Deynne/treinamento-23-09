package com.minsait.treinamento.model.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.minsait.treinamento.model.enumerators.TipoOperacao;

import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "Transacoes")
public class Transacao extends GenericEntity<Long> {

    @JoinColumn(nullable = false, name = "id_conta")
    @ManyToOne(fetch = FetchType.LAZY)
    private Conta conta;
    
    @Column(nullable = false)
    @Default
    private Date data = new Date();
    
    @Column(nullable = false)
    private TipoOperacao operacao;

    @Column(nullable = false)
    private Double valor;
    
    @JoinColumn(name = "id_contaOperacao")
    @OneToOne(optional = true, fetch = FetchType.LAZY)
    private Conta contaOperacao;
    
}

