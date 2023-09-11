package com.minsait.treinamento.utils;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.springframework.util.StringUtils;

import com.minsait.treinamento.exceptions.BundleNotDefinedException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MessageUtil {
    public static ResourceBundle bundle;
    
    public static boolean hasMessage(String key) {
        
        try {
            return getBundle().getObject(key) != null;
        } catch (MissingResourceException mre) {
            return false;
        }
    }

    private static ResourceBundle getBundle() {
        if(bundle == null) {
            throw new BundleNotDefinedException();
        }
        return bundle;
    }

    /**
    * Recupera uma mensagem do arquivo de properties.
    * @param key A chave da propriedade.
    * @return O valor da propriedade.
    */
    public static String getMessage(String key) {
        return getMessage(getBundle(), key);
    }
    
    /**
    * @author Eder Ferreira
    * Metodo que retorna a descricao de uma mensagem informada no arquivo .properties
    * @param bundle
    * @param codMsg
    * @param params
    * @return String
    */
    public static String getMessage(ResourceBundle bnd, String codMsg, String... params) {
         String retorno = null;
         if (bnd != null && codMsg != null) {
            boolean existeMsg = bnd.containsKey(codMsg);
            while (existeMsg) {
                retorno = bnd.getString(codMsg);
                if (StringUtils.hasLength(retorno)) {
                    if (retorno.startsWith("${") && retorno.trim().endsWith("}")) {
                        codMsg = retorno.replaceFirst("\\$", "").replaceFirst("\\{", "").replaceFirst("\\}", "").trim();
                        existeMsg = bnd.containsKey(codMsg);
                    } else {
                        existeMsg = false;
                    }
                } else {
                existeMsg = false;
                }
            }
        }
        return getTextMessageReplace(retorno, params);
    }
    
    /**
    * Metodo que sobrescreve os valores dos parametros, na descricao de uma mensagem
    * @param descMsg
    * @param params
    * @return String
    */
    public static String getTextMessageReplace(String descMsg, String... params) {
        if (descMsg != null && params != null) {
            MessageFormat mf = new MessageFormat(descMsg);
            descMsg = mf.format(params, new StringBuffer(), null).toString();
        }
        return descMsg;
    }
    
    /**
    * Metodo que sobrescreve os valores dos parametros, na descricao de uma mensagem obtida atraves do
    * arquivo .properties pelo codigo da mensagem
    * 
    * @param codMsg
    * @param values
    * @return String
    */
    public static String getMessageReplace(String codMsg, String... params) {
        if(bundle.containsKey(codMsg)) {
            String line = getBundle().getString(codMsg);
            return getTextMessageReplace(line, params);
        }
        return null;
        
    }

    public static void setBundle(ResourceBundle customResourceBundle) {
        bundle = customResourceBundle;
    }
}
