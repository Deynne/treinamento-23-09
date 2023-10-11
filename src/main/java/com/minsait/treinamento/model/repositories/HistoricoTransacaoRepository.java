package com.minsait.treinamento.model.repositories;

import com.minsait.treinamento.model.entities.HistoricoTransacao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HistoricoTransacaoRepository extends GenericCrudRepository<HistoricoTransacao, Long> {

    @Query("select h from HistoricoTransacao h where "
            + "(h.contaPrincipal.numAgencia = ?1 and h.contaPrincipal.numConta = ?2) "
            + "order by h.timestamp")
    List<HistoricoTransacao> findAllByAgenciaAndConta(String numAgencia, String numConta);

    List<HistoricoTransacao> findByTimestampBetweenAndContaPrincipal_Id(
            LocalDateTime start, LocalDateTime end, Long contaPrincipal_id);

}
