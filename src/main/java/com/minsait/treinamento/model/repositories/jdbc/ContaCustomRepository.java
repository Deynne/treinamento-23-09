package com.minsait.treinamento.model.repositories.jdbc;

import com.minsait.treinamento.model.entities.Conta;

public interface ContaCustomRepository {

    public Conta acharPorUsuarioOuNumAgenciaEContaEntityManager(Long idUsuario, String numAgencia, String numConta);

    Conta acharPorUsuarioOuNumAgenciaEContaJDBCTemplate(Long idUsuario, String numAgencia, String numConta);
}
