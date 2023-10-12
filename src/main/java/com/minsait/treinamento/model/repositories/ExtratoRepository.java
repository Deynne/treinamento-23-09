package com.minsait.treinamento.model.repositories;

import java.util.Optional;
import org.springframework.stereotype.Repository;

import com.minsait.treinamento.model.entities.SolicExtrato;

@Repository
public interface ExtratoRepository extends GenericCrudRepository<SolicExtrato, Long>{
    
    Optional<SolicExtrato> findByVerificador(String verificador);

}
