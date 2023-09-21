package com.minsait.treinamento.model.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.minsait.treinamento.dtos.IdentificadorBasicoDTO;
import com.minsait.treinamento.model.entities.Usuario;

@Repository
public interface UsuarioRepository extends GenericCrudRepository<Usuario, Long> {

    //TODO Implementar e mostrar como isto funciona
    @Query(value = "select new com.minsait.treinamento.dtos.IdentificadorBasicoDTO(u.id,u.nome) from Usuario u where u.id = ?1")
    IdentificadorBasicoDTO<Long> findUsuario(Long id);
}
