package com.minsait.treinamento.model.repositories;

import com.minsait.treinamento.model.embedded.Documentacao;
import org.springframework.stereotype.Repository;

import com.minsait.treinamento.model.entities.Usuario;

@Repository
public interface UsuarioRepository extends GenericCrudRepository<Usuario, Long> {

    boolean existsUsuarioByDocumentacao_CpfOrDocumentacao_rg(String cpf, String rg);

}
