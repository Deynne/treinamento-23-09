package com.minsait.treinamento.model.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.minsait.treinamento.model.entities.Conta;
import com.minsait.treinamento.model.entities.Usuario;
import com.minsait.treinamento.model.repositories.jdbc.ContaCustomRepository;

@Repository
public interface ContaRepository extends GenericCrudRepository<Conta, Long>, ContaCustomRepository {

    List<Conta> findAllByUsuarioOrderBySaldoDesc(Usuario u);
    
    @Query("select c from Conta c where c.usuario = ?1 and c.saldo >= ?2 order by c.saldo desc")
    List<Conta> acharContasComDinheiroOrdemParametro(Usuario u, double valorMinimo);
    
    @Query("select c from Conta c where c.usuario = :usuario and c.saldo >= :valorMinimo order by c.saldo desc")
    List<Conta> acharContasComDinheiroNomeParametro(Usuario usuario, double valorMinimo);
    
    @Query(value = "select c.* from conta c where c.id_usuario = ?1 and c.saldo >= ?2 order by c.saldo desc",
           nativeQuery = true)
    List<Conta> acharContasComDinheiroQueryNativa(Usuario usuario, double valorMinimo);
    
    @Query("select c from Conta c where c.usuario.nome = :nome order by c.saldo desc")
    List<Conta> achaContasPorNomeUsuario(String nome);
    
    @Query(value = "select c.* from conta c "
            + "inner join usuario u on u.id = c.id_usuario "
            + "where u.nome = :nome order by c.saldo desc",
           nativeQuery = true)
    List<Conta> achaContasPorNomeUsuarioQueryNativa(String nome);

    @Query("select c from Conta c where c.usuario.id = ?1")
    public List<Conta> findAllByIdUsuario(long idUsuario);
    
    Optional<Conta> findByNumAgenciaAndNumConta(String numAgencia, String numConta);

    
}
