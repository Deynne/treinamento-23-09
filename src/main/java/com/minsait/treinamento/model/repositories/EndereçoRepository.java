package com.minsait.treinamento.model.repositories;

import org.springframework.stereotype.Repository;

import com.minsait.treinamento.model.entities.Endereço;

@Repository
public interface EndereçoRepository extends GenericCrudRepository<Endereço, Long> {
	
}
