package com.minsait.treinamento.exceptions.interfaces;

public interface GenericCustomMessage {
    
    /**
     * Código que referência a Mensagem Principal encontrada no arquivo {@link messages.properties}
     *
     * @return String
     */
    public abstract String getCodigoMsg();
        
    /**
     * Código que referência a Mensagem Auxiliar encontrada no arquivo {@link messages.properties}.
     *
     * @return String
     */
    public abstract String getCodigoMsgAuxiliar();
    
    /**
     * O texto que representa a mensagem do codigo do arquivo {@link messages.properties}.
     *
     * @return String
     */
    public abstract String getDescricaoMsg(String... params);
    
    /**
     * O texto que representa a mensagem Auxiliar do codigo do arquivo {@link messages.properties}.
     *
     * @return String
     */
    public abstract String getDescricaoMsgAuxiliar(String... params);
    
    /**
     * Severidade dita a forma que será formatado o texto no sistema web.
     *
     * Caso não seja passado, por default a severidade é SEVERIDADE_ERRO (1).
     *
     * @return int
     */
    public abstract String getSeveridade();
    
}
