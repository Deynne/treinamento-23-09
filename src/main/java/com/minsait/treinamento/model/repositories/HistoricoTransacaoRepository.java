package com.minsait.treinamento.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.minsait.treinamento.model.entities.HistoricoTransacao;

@Repository
public interface HistoricoTransacaoRepository extends GenericCrudRepository<HistoricoTransacao, Long> {

    @Query("select h from HistoricoTransacao h where "
            + "(h.contaPrincipal.numAgencia = ?1 and h.contaPrincipal.numConta = ?2) "
            + "order by h.timestamp")
    List<HistoricoTransacao> findAllByAgenciaAndConta(String numAgencia, String numConta);

}
