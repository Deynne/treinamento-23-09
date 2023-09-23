package com.minsait.treinamento.model.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minsait.treinamento.dtos.endereco.EnderecoDTO;
import com.minsait.treinamento.dtos.endereco.EnderecoInsertDTO;
import com.minsait.treinamento.dtos.endereco.EnderecoUpdateDTO;
import com.minsait.treinamento.exceptions.GenericException;
import com.minsait.treinamento.exceptions.MensagemPersonalizada;
import com.minsait.treinamento.model.entities.Endereco;
import com.minsait.treinamento.model.entities.Usuario;
import com.minsait.treinamento.model.repositories.EnderecoRepository;

@Service
public class EnderecoService extends GenericCrudServiceImpl<EnderecoRepository, Long, EnderecoInsertDTO, EnderecoUpdateDTO, EnderecoDTO> {

    @Autowired
    private UsuarioService usuarioService;
    
    @Override
    public EnderecoDTO salvar(@Valid EnderecoInsertDTO dto) {
        Usuario u = this.usuarioService.encontrarEntidadePorId(dto.getIdUsuario());
        Endereco e = Endereco.builder()
                             .bairro(dto.getBairro())
                             .cep(dto.getCep())
                             .cidade(dto.getCidade())
                             .numero(dto.getNumero())
                             .referencia(dto.getReferencia())
                             .rua(dto.getRua())
                             .usuario(u)
                             .build();
        
        e = this.repository.save(e);
        
        return toDTO(e);
    }

    public static EnderecoDTO toDTO(Endereco e) {
        return EnderecoDTO.builder()
                .id(e.getId())
                .bairro(e.getBairro())
                .cep(e.getCep())
                .cidade(e.getCidade())
                .numero(e.getNumero())
                .referencia(e.getReferencia())
                .rua(e.getRua())
                .idUsuario(e.getUsuario().getId())
                .build();
    }

    @Override
    public EnderecoDTO atualizar(@Valid EnderecoUpdateDTO dto) {
        Endereco e = this.repository.findById(dto.getId())
                        .orElseThrow(() -> new GenericException(MensagemPersonalizada.ALERTA_ELEMENTO_NAO_ENCONTRADO,
                                                                    Endereco.class.getSimpleName()));
        if(dto.getBairro() != null) {
            e.setBairro(dto.getBairro());
        }
        
        if(dto.getCep() != null) {
            e.setCep(dto.getCep());
        }
        
        if(dto.getCidade() != null) {
            e.setCidade(dto.getCidade());
        }
        
        if(dto.getNumero() != null) {
            e.setNumero(dto.getNumero());
        }
        
        if(dto.getReferencia() != null) {
            e.setReferencia(dto.getReferencia());
        }

        if(dto.getRua() != null) {
            e.setRua(dto.getRua());
        }
        
        if(dto.getIdUsuario() != null) {
            Usuario u = this.usuarioService.encontrarEntidadePorId(dto.getIdUsuario());
            e.setUsuario(u);;
        }
            this.repository.save(e);
        return toDTO(e);
    }

    @Override
    public EnderecoDTO excluir(@NotNull @Positive Long id) {
        Endereco e = this.repository.findById(id)
                .orElseThrow(() -> new GenericException(MensagemPersonalizada.ALERTA_ELEMENTO_NAO_ENCONTRADO,
                                                            Endereco.class.getSimpleName()));
        
        this.repository.delete(e);
        
        return toDTO(e);
    }

    @Override
    public EnderecoDTO encontrarPorId(@NotNull @Positive Long id) {
        return toDTO(this.repository.findById(id)
                .orElseThrow(() -> new GenericException(MensagemPersonalizada.ALERTA_ELEMENTO_NAO_ENCONTRADO,
                        Endereco.class.getSimpleName())));
    }

    @Override
    public List<EnderecoDTO> encontrarTodos() {
        return this.repository.findAll().stream()
                                        .map(EnderecoService::toDTO)
                                        .collect(Collectors.toList());
    }

    public void excluirPorIdUsuario(@NotNull @Positive Long id) {
        List<Endereco> es = this.repository.findAllByidUsuario(id);
        
        this.repository.deleteAll(es);
    }

}
