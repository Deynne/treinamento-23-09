package com.minsait.treinamento.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import com.minsait.treinamento.utils.MessageUtil;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class MessageConfig {
    
    private  final String nome;
    
    private final String extensao;
    
    private final Charset charset;
    
    private Control control;
    
    
    public MessageConfig(@Value("${app.custom-message.bundle.name:messages}") 
                         String nome,
                         @Value("${app.custom-message.bundle.extension:properties}") 
                         String extensao,
                         @Value("${app.custom-message.bundle.charset:java.nio.charset.StandardCharsets.UTF_8}")
                         String charset) {
        
        this.nome = nome;
        this.extensao = extensao;
        
        try {
            int indice = charset.lastIndexOf('.');
            if(indice < 0) {
                throw new ClassNotFoundException("Nenhuma classe foi especificada para o charset");
            }
            
            String tipo = charset.substring(indice+1);
            if(tipo.isBlank()) {
                throw new ClassNotFoundException("Nenhum tipo de charset foi especificada");
            }
            this.charset = (Charset) Class.forName(charset.substring(0, indice)).getField(tipo).get(null);
        }
        catch(ClassNotFoundException e) {
            log.error("Classe ".concat(charset).concat(" não encontrada."), e);
            throw new RuntimeException(e);
        } catch (IllegalArgumentException | 
                 IllegalAccessException |
                 NoSuchFieldException |
                 SecurityException e) {
            throw new RuntimeException(e);
        }
        this.control = new CustomControl();
    }

    @PostConstruct
    public void init() {
        MessageUtil.setBundle(new CustomResourceBundle());
    }
    
    private class CustomResourceBundle extends ResourceBundle {
        public CustomResourceBundle() {
            this.setParent(ResourceBundle.getBundle(MessageConfig.this.nome,Locale.getDefault(),MessageConfig.this.control));
        }

        @Override
        protected Object handleGetObject(String key) {
            return this.parent.getObject(key);
        }

        @Override
        public Enumeration<String> getKeys() {
            return this.parent.getKeys();
        }
    }
    
    private class CustomControl extends Control {
        @Override
        public ResourceBundle newBundle(String baseName, 
                                        Locale locale, 
                                        String format, 
                                        ClassLoader loader,
                                        boolean reload) throws IllegalAccessException, 
                                                               InstantiationException, 
                                                               IOException {
            
            final String bundleName = this.toBundleName(baseName, locale);
            final String resourceName = this.toResourceName(bundleName, MessageConfig.this.extensao);
            
            ResourceBundle bundle = null;
            InputStream stream = null;
            
            if (reload) {
                final URL url = loader.getResource(resourceName);
                
                if (url != null) {
                    final URLConnection connection = url.openConnection();
                    
                    if (connection != null) {
                        connection.setUseCaches(false);
                        stream = connection.getInputStream();
                    }
                }
            } else {
                stream = loader.getResourceAsStream(resourceName);
            }
            
            if (stream != null) {
                try (InputStreamReader isr = new InputStreamReader(stream, MessageConfig.this.charset)){
                    bundle = new PropertyResourceBundle(isr);
                }
            }
            return bundle;
        }
        
    }

}
