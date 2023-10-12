package com.minsait.treinamento.model.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.minsait.treinamento.model.entities.Conta;
import com.minsait.treinamento.model.entities.Transacao;
import com.minsait.treinamento.model.entities.Usuario;

@Repository
public interface TransacaoRepository extends GenericCrudRepository<Transacao, Long> {

    @Query("select t from Transacao t where t.conta.usuario = :usuario order by t.data desc")
    List<Transacao> acharPorUsuario(Usuario usuario);
    
    @Query("select t from Transacao t where t.conta = :conta order by t.data desc")
    List<Transacao> acharPorConta(Conta conta);
    
    @Query("select t from Transacao t where t.conta = :conta and t.data >= :aPartirDe and t.data <= :dataFim order by t.data desc")
    List<Transacao> acharPorContaEDatas(Conta conta, Date aPartirDe, Date dataFim);
}
