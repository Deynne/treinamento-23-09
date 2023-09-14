package com.minsait.treinamento.model.service;

import com.minsait.treinamento.dtos.conta.ContaDTO;
import com.minsait.treinamento.model.repositories.ContaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContaService extends GenericCrudServiceImpl<ContaRepository, Long, ContaDTO, ContaDTO, ContaDTO> {

    @Override
    public ContaDTO salvar(ContaDTO dto) {
        return null;
    }

    @Override
    public ContaDTO atualizar(ContaDTO dto) {
        return null;
    }

    @Override
    public ContaDTO excluir(Long id) {
        return null;
    }

    @Override
    public ContaDTO encontrarPorId(Long id) {
        return null;
    }

    @Override
    public List<ContaDTO> encontrarTodos() {
        return null;
    }
}
