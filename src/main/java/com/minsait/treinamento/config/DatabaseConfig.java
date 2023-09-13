package com.minsait.treinamento.config;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;


@Component
@EnableJpaRepositories(basePackages = {"com.minsait.treinamento.model.repositories"})
public class DatabaseConfig {

    
}
