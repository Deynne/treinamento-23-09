package com.minsait.treinamento.model.service;

import com.minsait.treinamento.exceptions.GenericException;
import com.minsait.treinamento.exceptions.MensagemPersonalizada;
import com.minsait.treinamento.model.entities.HistoricoExtrato;
import com.minsait.treinamento.model.repositories.HistoricoExtratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.UUID;

@Service
public class HistoricoExtratoService {

    @Autowired
    private HistoricoExtratoRepository extratoRepository;

    @Transactional
    public HistoricoExtrato salvar(@Valid HistoricoExtrato extrato) {

        HistoricoExtrato extratoSave = HistoricoExtrato.builder()
                .codigo(UUID.randomUUID())
                .dataInicial(extrato.getDataInicial())
                .dataFinal(extrato.getDataFinal())
                .idContaOrigem(extrato.getIdContaOrigem())
                .build();

        return extratoRepository.save(extratoSave);
    }

    public boolean isExisteExtrato(UUID codigo) {
        boolean existe = extratoRepository.existsByCodigo(codigo);
        if (!existe) {
            throw new GenericException(MensagemPersonalizada.ALERTA_ELEMENTO_NAO_ENCONTRADO,
                    HistoricoExtrato.class.getSimpleName());
        }
        return true;
    }

    public HistoricoExtrato encontrarPorCodigo(UUID codigo) {

        return extratoRepository.findByCodigo(codigo);
    }
}
