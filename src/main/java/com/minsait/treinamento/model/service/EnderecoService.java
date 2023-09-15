package com.minsait.treinamento.model.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.minsait.treinamento.dtos.endereco.EnderecoDTO;
import com.minsait.treinamento.dtos.endereco.EnderecoInsertDTO;
import com.minsait.treinamento.dtos.endereco.EnderecoUpdateDTO;
import com.minsait.treinamento.exceptions.GenericException;
import com.minsait.treinamento.exceptions.MensagemPersonalizada;
import com.minsait.treinamento.model.entities.Endereco;
import com.minsait.treinamento.model.repositories.EnderecoRepository;

@Service
public class EnderecoService extends GenericCrudServiceImpl<EnderecoRepository, Long, EnderecoInsertDTO, EnderecoUpdateDTO, EnderecoDTO>{

    @Autowired
    private EnderecoRepository repositorio;
    
    private static EnderecoDTO toDTO(Endereco e) {
        return EnderecoDTO.builder()
                .id(e.getId())
                .Cidade(e.getCidade())
                .Bairro(e.getBairro())
                .Rua(e.getRua())
                .Numero(e.getNumero())
                .CEP(e.getCEP())
                .Referencia(e.getReferencia())
                .build();
    }

    
    @Override
    public EnderecoDTO atualizar(EnderecoUpdateDTO dto) {        
        Endereco endereco = this.repositorio.findById(dto.getId())
                                .orElseThrow(() -> new GenericException(
                                                        MensagemPersonalizada.ALERTA_ELEMENTO_NAO_ENCONTRADO,
                                                        Endereco.class.getSimpleName()));
        
        if(StringUtils.hasText(dto.getCidade()))
            endereco.setCidade(dto.getCidade());
        if(StringUtils.hasText(dto.getBairro()))
            endereco.setBairro(dto.getBairro());
        if(StringUtils.hasText(dto.getRua()))
            endereco.setRua(dto.getRua());
        if(dto.getNumero() != null)
            endereco.setNumero(dto.getNumero());
        if(StringUtils.hasText(dto.getReferencia()))
            endereco.setReferencia(dto.getReferencia());
        if(StringUtils.hasText(dto.getCEP()))
            endereco.setCEP(dto.getCEP().replaceAll("[.-]*", ""));
        
        return toDTO(this.repositorio.save(endereco));
    }
    
    
    @Override
    public EnderecoDTO excluir(Long id) {
        Endereco endereco = this.repositorio.findById(id)
                                .orElseThrow(() -> new GenericException(
                                                    MensagemPersonalizada.ALERTA_ELEMENTO_NAO_ENCONTRADO,
                                                    Endereco.class.getSimpleName()));
        this.repositorio.deleteById(id);
        return toDTO(endereco);
    }
    
    
    @Override
    public EnderecoDTO encontrarPorId(Long id) {
        return toDTO(this.repositorio.findById(id)
                         .orElseThrow(() -> new GenericException(
                                                  MensagemPersonalizada.ALERTA_ELEMENTO_NAO_ENCONTRADO,
                                                  Endereco.class.getSimpleName())));
    }
    
    
    @Override
    public List<EnderecoDTO> encontrarTodos() {
        return this.repository
                   .findAll()
                   .stream()
                   .map(EnderecoService::toDTO)
                   .collect(Collectors.toList());
    }
    
    
    @Override
    public EnderecoDTO salvar(EnderecoInsertDTO dto) {
        Endereco endereco = Endereco.builder()
                .Cidade(dto.getCidade())
                .Bairro(dto.getBairro())
                .Rua(dto.getRua())
                .Numero(dto.getNumero())
                .CEP(dto.getCEP().replaceAll("[.-]*", ""))
                .Referencia(dto.getReferencia())
                .build();
        
        return toDTO(this.repositorio.save(endereco));
    }
    
}
