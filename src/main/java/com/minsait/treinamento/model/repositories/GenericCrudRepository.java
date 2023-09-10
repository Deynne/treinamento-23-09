package com.minsait.treinamento.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.minsait.treinamento.model.entities.GenericEntity;

@NoRepositoryBean
public interface GenericCrudRepository<E extends GenericEntity<I>,I extends Number> 
                    extends JpaRepository<E,I>{

}
