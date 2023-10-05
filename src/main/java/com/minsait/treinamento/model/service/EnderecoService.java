package com.minsait.treinamento.model.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.minsait.treinamento.dtos.endereco.EnderecoDTO;
import com.minsait.treinamento.dtos.endereco.EnderecoInsertDTO;
import com.minsait.treinamento.dtos.endereco.EnderecoUpdateDTO;
import com.minsait.treinamento.exceptions.GenericException;
import com.minsait.treinamento.exceptions.MensagemPersonalizada;
import com.minsait.treinamento.model.entities.Endereco;
import com.minsait.treinamento.model.entities.Usuario;
import com.minsait.treinamento.model.repositories.EnderecoRepository;

@Service
public class EnderecoService extends GenericCrudServiceImpl<EnderecoRepository, Long, EnderecoInsertDTO, EnderecoUpdateDTO, EnderecoDTO>{

    @Autowired
    private UsuarioService usuarioService;
    
    private static EnderecoDTO toDTO(Endereco e) {
        return EnderecoDTO.builder()
                .id(e.getId())
                .Cidade(e.getCidade())
                .Bairro(e.getBairro())
                .Rua(e.getRua())
                .Numero(e.getNumero())
                .CEP(e.getCEP())
                .Referencia(e.getReferencia())
                .UsuarioId(e.getUsuario().getId())
                .build();
    }

    
    @Override
    @Transactional
    public EnderecoDTO atualizar(EnderecoUpdateDTO dto) {        
        Endereco endereco = this.repository.findById(dto.getId())
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
            endereco.setCEP(dto.getCEP());
        if(dto.getUsuarioId() != null) {
            Usuario u = this.usuarioService.encontrarEntidadePorId(dto.getUsuarioId());
            if( ! u.getId().equals(endereco.getUsuario().getId()))
                endereco.setUsuario(u);
        }
        
            return toDTO(this.repository.save(endereco));
    }
    
    
    @Override
    public EnderecoDTO excluir(Long id) {
        Endereco endereco = this.repository.findById(id)
                                .orElseThrow(() -> new GenericException(
                                                    MensagemPersonalizada.ALERTA_ELEMENTO_NAO_ENCONTRADO,
                                                    Endereco.class.getSimpleName()));
        this.repository.deleteById(id);
        return toDTO(endereco);
    }
    
    
    @Override
    public EnderecoDTO encontrarPorId(Long id) {
        return toDTO(this.repository.findById(id)
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
        Usuario u = this.usuarioService.encontrarEntidadePorId(dto.getUsuarioId());
        Endereco endereco = Endereco.builder()
                .Cidade(dto.getCidade())
                .Bairro(dto.getBairro())
                .Rua(dto.getRua())
                .Numero(dto.getNumero())
                .CEP(dto.getCEP())
                .Referencia(dto.getReferencia())
                .usuario(u)
                .build();
        
        return toDTO(this.repository.save(endereco));
    }


    public List<EnderecoDTO> acharPorUsuarioId(Long id) {
        Usuario u = this.usuarioService.encontrarEntidadePorId(id);    
        return this.repository
                .acharPorUsuarioId(u)
                .stream()
                .map(EnderecoService::toDTO)
                .collect(Collectors.toList());
    }


    public int excluirPorUsuario(Usuario u) {
        return this.repository.excluirTodosPorUsuario(u);        
    }
    
}
