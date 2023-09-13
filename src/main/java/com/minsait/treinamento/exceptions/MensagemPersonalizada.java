package com.minsait.treinamento.exceptions;

import org.springframework.util.StringUtils;

import com.minsait.treinamento.exceptions.enumerators.TipoMensagem;
import com.minsait.treinamento.exceptions.interfaces.GenericCustomMessage;
import com.minsait.treinamento.utils.MessageUtil;

public enum MensagemPersonalizada implements GenericCustomMessage {
    ERRO_INESPERADO("inesperado",null,TipoMensagem.ERRO),
    ERRO_JSON_SERIALIZACAO("json.serializacao",null,TipoMensagem.ERRO),
    ERRO_VALIDACAO_CAMPO("validacao.campo",null,TipoMensagem.ERRO),
    ERRO_ACESSO_JPA("acesso.jpa",null,TipoMensagem.ERRO),
    ERRO_PARAMETROS_INVALIDOS("parametros.invalidos",null,TipoMensagem.ERRO),
    ERRO_INTEGRIDADE_DO_BANCO_VIOLADA("integridade.banco.violada",null,TipoMensagem.ERRO),
    ERRO_ACESSO_NEGADO("acesso.negado",null,TipoMensagem.ERRO),
    ERRO_METODO_NAO_SUPORTADO("metodo.nao.suportado",null,TipoMensagem.ERRO),
    
 // Campos Constraints
    ERRO_CONSTRAINT_CAMPO_OBRIGATORIO("constraint.campo.obrigatorio", null, TipoMensagem.ERRO),
    ERRO_CONSTRAINT_CAMPO_POSITIVO("constraint.campo.positivo", null, TipoMensagem.ERRO),
    ERRO_CONSTRAINT_CAMPO_POSITIVO_OU_ZERO("constraint.campo.positivo.ou.zero", null, TipoMensagem.ERRO),
    ERRO_CONSTRAINT_CAMPO_NEGATIVO("constraint.campo.negativo", null, TipoMensagem.ERRO),
    ERRO_CONSTRAINT_CAMPO_NEGATIVO_OU_ZERO("constraint.campo.negativo.ou.zero", null, TipoMensagem.ERRO),
    ERRO_CONSTRAINT_CAMPO_NULO("constraint.campo.nulo", null, TipoMensagem.ERRO),
    ERRO_CONSTRAINT_CAMPO_NAO_NULO("constraint.campo.nao.nulo", null, TipoMensagem.ERRO),
    ERRO_CONSTRAINT_CAMPO_NAO_VAZIO("constraint.campo.nao.vazio", null, TipoMensagem.ERRO),
    ERRO_CONSTRAINT_CAMPO_TAMANHO("constraint.campo.tamanho", null, TipoMensagem.ERRO),
    ERRO_CONSTRAINT_CAMPO_TAMANHO_MAXIMO("constraint.campo.tamanho.maximo", null, TipoMensagem.ERRO),
    ERRO_CONSTRAINT_CAMPO_TAMANHO_MINIMO("constraint.campo.tamanho.minimo", null, TipoMensagem.ERRO),
    ERRO_CONSTRAINT_CAMPO_MAXIMO("constraint.campo.maximo", null, TipoMensagem.ERRO),
    ERRO_CONSTRAINT_CAMPO_MINIMO("constraint.campo.minimo", null, TipoMensagem.ERRO),
    ERRO_CONSTRAINT_CAMPO_FALSO("constraint.campo.falso", null, TipoMensagem.ERRO),
    ERRO_CONSTRAINT_CAMPO_VERDADEIRO("constraint.campo.verdadeiro", null, TipoMensagem.ERRO),
    ERRO_CONSTRAINT_CAMPO_DIGITOS("constraint.campo.digitos", null, TipoMensagem.ERRO),
    ERRO_CONSTRAINT_CAMPO_EMAIL("constraint.campo.email", null, TipoMensagem.ERRO),
    ERRO_CONSTRAINT_CAMPO_DATA_FUTURA("constraint.campo.data.futura", null, TipoMensagem.ERRO),
    ERRO_CONSTRAINT_CAMPO_DATA_PASSADA("constraint.campo.data.passada", null, TipoMensagem.ERRO),
    ERRO_CONSTRAINT_CAMPO_DATA_FUTURA_OU_PRESENTE("constraint.campo.data.futura.ou.presente", null, TipoMensagem.ERRO),
    ERRO_CONSTRAINT_CAMPO_DATA_PASSADA_OU_PRESENTE("constraint.campo.passada.ou.presente", null, TipoMensagem.ERRO),
    ERRO_CONSTRAINT_CAMPO_PADRAO_REGEX("constraint.campo.padrao.regex", null, TipoMensagem.ERRO),
    
    ALERTA_ELEMENTO_NAO_ENCONTRADO("elemento.nao.encontrado", null, TipoMensagem.ALERTA);
    
    private String codigoMsg;
    private String codigoMsgAux;
    private TipoMensagem severidade;

    
    
    private MensagemPersonalizada(String codigoMsg, String codigoMsgAux, TipoMensagem severidade) {
        this.codigoMsg = severidade.getDescricao().concat(".").concat(codigoMsg);
        this.codigoMsgAux = !StringUtils.hasLength(codigoMsgAux) ? null :
                                                                   severidade.getDescricao()
                                                                           .concat(".aux.")
                                                                           .concat(codigoMsgAux);
        this.severidade = severidade;
    }
    @Override
    public String getCodigoMsg() {
        return this.codigoMsg;
    }
    @Override
    public String getCodigoMsgAuxiliar() {
        return this.codigoMsgAux;
    }
    
    @Override
      public final String getDescricaoMsg(final String... params) {
//        return FacesUtil.obterTextoMessagesProperties(this.getCodigoMsg(), params);
          return MessageUtil.getMessageReplace(codigoMsg, params);
    }
    @Override
    public String getDescricaoMsgAuxiliar(String... params) {
        if(!StringUtils.hasLength(codigoMsgAux))
            return null;
        return MessageUtil.getMessageReplace(codigoMsgAux);
//        return FacesUtil.obterTextoMessagesProperties(this.getCodigoMsgAuxiliar(), params);
    }
    @Override
    public String getSeveridade() {
        return this.severidade.getDescricao();
    }
}
