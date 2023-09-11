package com.minsait.treinamento.exceptions.enumerators;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TipoMensagem {
    LOG("log"),
    ERRO("erro"),
    ALERTA("alerta");
    
    private String descricao;
}
