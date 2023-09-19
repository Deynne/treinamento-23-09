package com.minsait.treinamento.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.minsait.treinamento.dtos.IdentificadorBasicoDTO;
import com.minsait.treinamento.model.entities.Conta;
import com.minsait.treinamento.model.entities.Usuario;

@Repository
public interface UsuarioRepository extends GenericCrudRepository<Usuario, Long> {
    
    @Modifying(clearAutomatically = true)
    @Query("update Conta c set c.bloqueado = :bloqueio where c.usuario = :usuario")
    void bloqueiaTodasPorUsuario(Usuario usuario, Boolean bloqueio);
    
    //TODO Implementar e mostrar como isto funciona
//    @Query(value = "",nativeQuery = true)
//    IdentificadorBasicoDTO<Long> findUsuario(Long id);
}
