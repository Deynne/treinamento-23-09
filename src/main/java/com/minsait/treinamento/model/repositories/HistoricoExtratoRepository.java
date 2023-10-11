package com.minsait.treinamento.model.repositories;

import com.minsait.treinamento.model.entities.HistoricoExtrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HistoricoExtratoRepository extends JpaRepository<HistoricoExtrato, UUID> {

    boolean existsByCodigo(UUID codigo);

    HistoricoExtrato findByCodigo(UUID codigo);
}
