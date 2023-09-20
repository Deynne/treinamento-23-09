package com.minsait.treinamento.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.minsait.treinamento.model.entities.Endereco;

@Repository
public interface EnderecoRepository extends GenericCrudRepository<Endereco, Long> {

    @Query("select e from Endereco e where e.usuario.id = ?1")
    List<Endereco> findAllByidUsuario(Long idUsuario);

}
