package com.minsait.treinamento.model.enumerators;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TipoOperacao {

    CRIACAO("criacao"),
    DEPOSITO("deposito"),
    SAQUE("saque"),
    TRANSFERENCIA_ENVIO("envio_transferencia"),
    TRANSFERENCIA_RECEBIDA("recebe_transferencia");
    
    private String descricao;
    
}
