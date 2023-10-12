package com.minsait.treinamento.model.entities;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Builder.Default;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@Entity
@Table(name = "solicitacao_extrato")
public class SolicExtrato extends GenericEntity<Long>{
    
    @JoinColumn(nullable = false, name = "id_conta")
    @ManyToOne(fetch = FetchType.LAZY)
    private Conta conta;

    @Column(nullable = false, length=40)
    @Default
    private String verificador = UUID.randomUUID().toString();
    
    @Column(nullable = false)
    @Default
    private Date dataSolic = new Date(); // data final da busca
    
    @Column(nullable = false)
    private Date aPartirDe;

}
