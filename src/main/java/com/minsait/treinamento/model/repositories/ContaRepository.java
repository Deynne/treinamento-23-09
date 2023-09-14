package com.minsait.treinamento.model.repositories;

import com.minsait.treinamento.model.entities.Conta;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends GenericCrudRepository<Conta, Long> {
}
