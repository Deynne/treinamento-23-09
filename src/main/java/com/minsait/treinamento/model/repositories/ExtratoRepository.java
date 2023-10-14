package com.minsait.treinamento.model.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.minsait.treinamento.model.entities.Extrato;
import com.minsait.treinamento.model.entities.HistoricoTransacao;

@Repository
public interface ExtratoRepository extends GenericCrudRepository<Extrato, Long> {
	
	@Query("select e from Extrato e where e.identificador = :identificador")
	Extrato encontrarExtratoPorUuid(UUID identificador);
	
	@Query(value = "select e from Extrato e where "
            + "(e.contaVinculada.numAgencia = ?1 and e.contaVinculada.numConta = ?2 and e.timestamp >= (?3))"
            + "order by h.timestamp", nativeQuery = true)
    List<Extrato> findAllAfterDate(String numAgencia, String numConta, LocalDateTime timestamp);
}
