package com.minsait.treinamento.model.repositories.security;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.minsait.treinamento.model.entities.security.TokenJwt;
import com.minsait.treinamento.model.repositories.GenericCrudRepository;

@Repository
public interface TokenJwtRepository extends GenericCrudRepository<TokenJwt, Long> {

    TokenJwt findByToken(String token);

    @Query("select t from TokenJwt t where t.usuario.usuario = ?1")
    TokenJwt findByUsuario(String usuario);

    @Query("select t from TokenJwt t where t.dataTermino <= ?1")
    List<TokenJwt> findAllExpired(Instant agora);

}
