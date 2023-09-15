package com.minsait.treinamento.model.repositories;
 
import org.springframework.stereotype.Repository;

import com.minsait.treinamento.model.entities.Endereco;

@Repository
public interface EnderecoRepository extends GenericCrudRepository<Endereco, Long>{

}
