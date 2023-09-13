package com.minsait.treinamento.exceptions;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenericException extends RuntimeException {
    
    private MensagemPersonalizada validacao;
    
    private List<String> parametros;
    
    public GenericException(MensagemPersonalizada validacao, String ... params) {
        this(validacao);
        this.parametros = Arrays.asList(params);
    }
    
    public GenericException(MensagemPersonalizada validacao) {
        super(validacao.getCodigoMsg());
        this.validacao = validacao;
    }
    
    public GenericException(MensagemPersonalizada validacao, Throwable exception) {
        super(validacao.getCodigoMsg(),exception);
        this.validacao = validacao;
    }

    public GenericException(MensagemPersonalizada validacao, Throwable exception, String ... params) {
        this(validacao,exception);
        this.parametros = Arrays.asList(params);
    }
    
    public String getDescricaoMensagem() {
        return this.validacao.getDescricaoMsg(this.parametros.toArray(new String[0]));
    }
}
