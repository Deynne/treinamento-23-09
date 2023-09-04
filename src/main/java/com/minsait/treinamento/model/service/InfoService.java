package com.minsait.treinamento.model.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Service;

@Service
public class InfoService {

    @Autowired
    private Environment env;
    
    public Map<String,Object> getInfo() {
        Iterator<PropertySource<?>> sources = ((ConfigurableEnvironment) env)
                .getPropertySources().iterator();
          while(sources.hasNext()) {
              PropertySource<?> property = sources.next();
            if(property instanceof OriginTrackedMapPropertySource) {
                Map<String,Object> source = ((OriginTrackedMapPropertySource) property).getSource();
                
                Map<String,Object> resultado = new HashMap<>();
                
                source.keySet().forEach(key -> resultado.put(key, source.get(key).toString()));
                
                return resultado;
            }
          }
        return null;
    }
}
