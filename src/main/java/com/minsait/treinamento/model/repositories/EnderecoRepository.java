package com.minsait.treinamento.model.repositories;

import com.minsait.treinamento.model.entities.Endereco;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends GenericCrudRepository<Endereco, Long> {

}
