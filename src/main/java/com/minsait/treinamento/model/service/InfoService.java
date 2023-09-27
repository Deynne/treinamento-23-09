package com.minsait.treinamento.model.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;

import com.minsait.treinamento.exceptions.GenericException;
import com.minsait.treinamento.exceptions.MensagemPersonalizada;

@Service
public class InfoService {

    @Autowired
    private Environment env;
    
    @PostFilter("hasRole('ROLE_ADMIN') or !filterObject.matches(\".*(security|password|host|username|datasource).*\")")
    public List<String> getInfo() {
        Iterator<PropertySource<?>> sources = ((ConfigurableEnvironment) env)
                .getPropertySources().iterator();
          while(sources.hasNext()) {
              PropertySource<?> property = sources.next();
            if(property instanceof OriginTrackedMapPropertySource) {
                Map<String,Object> source = ((OriginTrackedMapPropertySource) property).getSource();
                
                TreeSet<String> chaves = new TreeSet<>(source.keySet());
                
                List<String> resultado = new ArrayList<>();
                
                chaves.forEach(key -> resultado.add(key.concat("=").concat(source.get(key).toString())));
                
                return resultado;
            }
          }
        return Collections.emptyList();
    }

    public Object throwException() {
        throw new GenericException(MensagemPersonalizada.ERRO_INESPERADO);
    }
}
