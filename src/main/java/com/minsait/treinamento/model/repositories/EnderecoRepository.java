package com.minsait.treinamento.model.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.minsait.treinamento.model.entities.Endereco;

@Repository
public interface EnderecoRepository extends GenericCrudRepository<Endereco, Long> {
	
	@Query(value = "select e.* from endereco e where e.cep = :cep", nativeQuery = true)
    List<Endereco> achaEnderecoPorCepQueryNativa(String cep);
}
