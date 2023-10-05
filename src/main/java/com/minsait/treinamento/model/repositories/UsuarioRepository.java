package com.minsait.treinamento.model.repositories;

import org.springframework.stereotype.Repository;

import com.minsait.treinamento.model.entities.Usuario;

@Repository
public interface UsuarioRepository extends GenericCrudRepository<Usuario, Long> {
    
    //TODO Implementar e mostrar como isto funciona
//    @Query(value = "",nativeQuery = true)
//    IdentificadorBasicoDTO<Long> findUsuario(Long id);
}
