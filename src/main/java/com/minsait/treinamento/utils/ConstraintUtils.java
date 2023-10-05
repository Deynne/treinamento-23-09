package com.minsait.treinamento.utils;

import java.util.Map;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.ObjectError;

import com.minsait.treinamento.exceptions.MensagemPersonalizada;

public class ConstraintUtils {
private final static String DESCRICAO_PADRAO = "Restrição não mapeada";
    
    private ConstraintUtils()  {}
    
    
    public static String getConstraintMessage(ObjectError error) {
        String descricao;
        String code = error.getCode();
        String field = ""; //= ((DefaultMessageSourceResolvable)error.getArguments()[0]).getCodes()[0];
        Object[] arguments = error.getArguments();
        for(Object o : arguments) {
            if(o instanceof DefaultMessageSourceResolvable) {
                DefaultMessageSourceResolvable temp = (DefaultMessageSourceResolvable) o;
                field = temp.getCodes()[0];
                break;
            }
        }
        
        switch (code) {
            case ConstantesConstraintsJavax.NULL:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_NULO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.NOT_NULL:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_NAO_NULO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.NOT_BLANK:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_NAO_VAZIO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.NOT_EMPTY:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_NAO_VAZIO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.POSITIVE:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_POSITIVO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.POSITIVE_OR_ZERO:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_POSITIVO_OU_ZERO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.NEGATIVE:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_NEGATIVO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.NEGATIVE_OR_ZERO:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_NEGATIVO_OU_ZERO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.SIZE:
                Integer max = (Integer) arguments[1];
                Integer min = (Integer) arguments[2];
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_TAMANHO.getDescricaoMsg(field,min.toString(),max.toString());
                break;
            case ConstantesConstraintsJavax.MAX:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_MAXIMO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.MIN:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_MINIMO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.ASSERT_FALSE:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_FALSO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.ASSERT_TRUE:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_VERDADEIRO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.DECIMAL_MAX:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_TAMANHO_MAXIMO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.DECIMAL_MIN:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_TAMANHO_MINIMO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.DIGITS:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_DIGITOS.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.EMAIL:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_EMAIL.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.FUTURE:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_DATA_FUTURA.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.FUTURE_OR_PRESENT:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_DATA_FUTURA_OU_PRESENTE.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.PAST:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_DATA_PASSADA.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.PAST_OR_PRESENT:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_DATA_PASSADA_OU_PRESENTE.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.PATTERN:
                String pattern = (String) error.getDefaultMessage();
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_PADRAO_REGEX.getDescricaoMsg(field,pattern);
                break;
            default:
                descricao = DESCRICAO_PADRAO;
        }
        return descricao;
    }
    
    public static String getConstraintMessage(String code, String field, Map<String,Object> argumentos) {
        if(code == null)
            return DESCRICAO_PADRAO;
        
        return getDescricao(code, field, argumentos);
    }
    
    public static String getConstraintMessage(String code, String field, Object ... argumentos) {
        
        //TODO refinar estas funções para adicionar os parametros novos
//      List<String> params = new ArrayList<>();
        
        if(code == null)
            return DESCRICAO_PADRAO;
        
        return getDescricao(code,field);
    }
    
    public static String getDescricao(String code, String field, Map<String,Object> argumentos) {
        
        String descricao;
        
        switch (code) {
            case ConstantesConstraintsJavax.NULL:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_NULO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.NOT_NULL:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_NAO_NULO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.NOT_BLANK:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_NAO_VAZIO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.NOT_EMPTY:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_NAO_VAZIO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.POSITIVE:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_POSITIVO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.POSITIVE_OR_ZERO:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_POSITIVO_OU_ZERO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.NEGATIVE:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_NEGATIVO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.NEGATIVE_OR_ZERO:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_NEGATIVO_OU_ZERO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.SIZE:
                Integer min = (Integer) argumentos.get("min");
                Integer max = (Integer) argumentos.get("max");
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_TAMANHO.getDescricaoMsg(field,min.toString(),max.toString());
                break;
            case ConstantesConstraintsJavax.MAX:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_MAXIMO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.MIN:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_MINIMO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.ASSERT_FALSE:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_FALSO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.ASSERT_TRUE:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_VERDADEIRO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.DECIMAL_MAX:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_TAMANHO_MAXIMO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.DECIMAL_MIN:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_TAMANHO_MINIMO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.DIGITS:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_DIGITOS.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.EMAIL:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_EMAIL.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.FUTURE:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_DATA_FUTURA.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.FUTURE_OR_PRESENT:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_DATA_FUTURA_OU_PRESENTE.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.PAST:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_DATA_PASSADA.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.PAST_OR_PRESENT:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_DATA_PASSADA_OU_PRESENTE.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.PATTERN:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_PADRAO_REGEX.getDescricaoMsg(field);
                break;
            default:
                descricao = DESCRICAO_PADRAO;
        }
        return descricao;
    }

    public static String getDescricao(String code, String field) {
        
        String descricao;
        
        switch (code) {
            case ConstantesConstraintsJavax.NULL:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_NULO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.NOT_NULL:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_NAO_NULO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.NOT_BLANK:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_NAO_VAZIO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.NOT_EMPTY:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_NAO_VAZIO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.POSITIVE:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_POSITIVO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.POSITIVE_OR_ZERO:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_POSITIVO_OU_ZERO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.NEGATIVE:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_NEGATIVO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.NEGATIVE_OR_ZERO:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_NEGATIVO_OU_ZERO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.SIZE:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_TAMANHO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.MAX:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_MAXIMO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.MIN:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_MINIMO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.ASSERT_FALSE:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_FALSO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.ASSERT_TRUE:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_VERDADEIRO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.DECIMAL_MAX:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_TAMANHO_MAXIMO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.DECIMAL_MIN:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_TAMANHO_MINIMO.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.DIGITS:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_DIGITOS.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.EMAIL:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_EMAIL.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.FUTURE:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_DATA_FUTURA.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.FUTURE_OR_PRESENT:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_DATA_FUTURA_OU_PRESENTE.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.PAST:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_DATA_PASSADA.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.PAST_OR_PRESENT:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_DATA_PASSADA_OU_PRESENTE.getDescricaoMsg(field);
                break;
            case ConstantesConstraintsJavax.PATTERN:
                descricao = MensagemPersonalizada.ERRO_CONSTRAINT_CAMPO_PADRAO_REGEX.getDescricaoMsg(field);
                break;
            default:
                descricao = DESCRICAO_PADRAO;
        }
        return descricao;
    }
    
    private static final class ConstantesConstraintsJavax {
        
        public static final String NULL = "Null";
        public static final String NOT_NULL = "NotNull";
        public static final String NOT_BLANK = "NotBlank";
        public static final String NOT_EMPTY = "NotEmpty";
        public static final String POSITIVE = "Positive";
        public static final String POSITIVE_OR_ZERO = "PositiveOrZero";
        public static final String NEGATIVE = "Negative";
        public static final String NEGATIVE_OR_ZERO = "NegativeOrZero";
        public static final String SIZE = "Size";
        public static final String MAX = "Max";
        public static final String MIN = "Min";
        public static final String ASSERT_FALSE = "AssertFalse";
        public static final String ASSERT_TRUE = "AssertTrue";
        public static final String DECIMAL_MAX = "DecimalMax";
        public static final String DECIMAL_MIN = "DecimalMin";
        public static final String DIGITS = "Digits";
        public static final String EMAIL = "Email";
        public static final String FUTURE = "Future";
        public static final String FUTURE_OR_PRESENT = "FutureOrPresent";
        public static final String PAST = "Past";
        public static final String PAST_OR_PRESENT = "PastOrPresent";
        public static final String PATTERN = "Pattern";
        
        private ConstantesConstraintsJavax() {}
    }
}
