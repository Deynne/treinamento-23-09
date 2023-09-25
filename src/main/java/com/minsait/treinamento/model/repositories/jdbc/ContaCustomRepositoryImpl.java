package com.minsait.treinamento.model.repositories.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.minsait.treinamento.exceptions.GenericException;
import com.minsait.treinamento.exceptions.MensagemPersonalizada;
import com.minsait.treinamento.model.entities.Conta;
import com.minsait.treinamento.model.entities.Usuario;

public class ContaCustomRepositoryImpl implements ContaCustomRepository {

    @Autowired
    private EntityManager em;
    
    @Autowired
    private JdbcTemplate template;
    
    @Override
    public Conta acharPorUsuarioOuNumAgenciaEContaEntityManager(Long idUsuario, String numAgencia, String numConta) {
        StringBuilder query = new StringBuilder();
        
        query.append("Select c.* from conta c where ");
        
        if(idUsuario != null) {
            query.append(" c.id_usuario = ?1 ");
        }
        else {
            query.append(" c.num_agencia = ?1 and c.num_conta = ?2 ");
        }
        
        query.append(" order by c.id limit 1");
        Query q = em.createNativeQuery(query.toString(), Conta.class);
        
        if(idUsuario != null) {
            q.setParameter(1, idUsuario);
        }
        else {
            q.setParameter(1, numAgencia);
            q.setParameter(2, numConta);
        }
        return (Conta) q.getSingleResult();
    }
    
    @Override
    public Conta acharPorUsuarioOuNumAgenciaEContaJDBCTemplate(Long idUsuario, String numAgencia, String numConta) {
        StringBuilder query = new StringBuilder();
        
        query.append("Select c.* from conta c where ");
        
        Object[] parametros;
        
        if(idUsuario != null) {
            query.append(" c.id_usuario = ? ");
            parametros = new Object[1];
            parametros[0] = idUsuario;
        }
        else {
            query.append(" c.num_agencia = ? and c.num_conta = ? ");
            parametros = new Object[2];
            parametros[0] = numAgencia;
            parametros[1] = numConta;
        }

        query.append(" order by c.id limit 1");
        return template.query(query.toString(), new ResultSetExtractor<Conta>() {

            @Override
            public Conta extractData(ResultSet rs) throws SQLException, DataAccessException {
                if(rs.next()) {
                    return Conta.builder()
                                .id(rs.getLong("id"))
                                .bloqueado(rs.getBoolean("bloqueado"))
                                .usuario(Usuario.builder().id(rs.getLong("id_usuario")).build())
                                .numAgencia(rs.getString("num_agencia"))
                                .numConta(rs.getString("num_conta"))
                                .saldo(rs.getDouble("saldo"))
                                .build();
                }
                else { 
                    throw new GenericException(MensagemPersonalizada.ALERTA_ELEMENTO_NAO_ENCONTRADO, Conta.class.getSimpleName());
                }
            }
            
        }, parametros);
    }

    
}
