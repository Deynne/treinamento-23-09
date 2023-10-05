package com.minsait.treinamento.model.repositories;
 
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.minsait.treinamento.model.entities.Endereco;
import com.minsait.treinamento.model.entities.Usuario;

@Repository
public interface EnderecoRepository extends GenericCrudRepository<Endereco, Long>{

    @Query("select e from Endereco e where e.usuario = ?1 order by e.id asc")
    List<Endereco> acharPorUsuarioId(Usuario u);
    
    @Modifying(flushAutomatically = true)
    @Query("delete from Endereco where usuario = :usuario")
    int excluirTodosPorUsuario(Usuario usuario);
}
