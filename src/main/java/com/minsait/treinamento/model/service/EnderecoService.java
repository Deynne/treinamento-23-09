package com.minsait.treinamento.model.service;

import com.minsait.treinamento.dtos.endereco.EnderecoDTO;
import com.minsait.treinamento.dtos.endereco.EnderecoInsertDTO;
import com.minsait.treinamento.dtos.endereco.EnderecoUpdateDTO;
import com.minsait.treinamento.exceptions.GenericException;
import com.minsait.treinamento.exceptions.MensagemPersonalizada;
import com.minsait.treinamento.model.entities.Endereco;
import com.minsait.treinamento.model.repositories.EnderecoRepository;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnderecoService extends GenericCrudServiceImpl<EnderecoRepository, Long, EnderecoInsertDTO, EnderecoUpdateDTO, EnderecoDTO> {
    @Override
    public EnderecoDTO salvar(@Valid EnderecoInsertDTO dto) {
        boolean enderecoExiste = this.repository.existsEnderecoByCepAndNumero(dto.getCep(), dto.getNumero());
        if (enderecoExiste) {
            throw new GenericException(MensagemPersonalizada.ERRO_INTEGRIDADE_DO_BANCO_VIOLADA, Endereco.class.getSimpleName());
        }
        Endereco endereco = toEntity(dto);
        return toDTO(this.repository.save(endereco));
    }

    @Override
    public EnderecoDTO atualizar(@Valid EnderecoUpdateDTO dto) {
        return this.repository.findById(dto.getId())
                .map(e -> {
                    e.setId(dto.getId());
                    e.setRua(dto.getRua());
                    e.setCep(dto.getCep());
                    e.setNumero(dto.getNumero());
                    e.setCidade(dto.getCidade());
                    e.setBairro(dto.getBairro());
                    e.setReferencia(dto.getReferencia());
                    return toDTO(this.repository.save(e));
                })
                .orElseThrow(() -> new GenericException(MensagemPersonalizada.ALERTA_ELEMENTO_NAO_ENCONTRADO, Endereco.class.getSimpleName()));
    }

    @Override
    public EnderecoDTO excluir(@NotNull @Positive Long id) {
        Endereco e = this.repository.findById(id)
                .orElseThrow(() -> new GenericException(MensagemPersonalizada.ALERTA_ELEMENTO_NAO_ENCONTRADO, Endereco.class.getSimpleName()));
        this.repository.delete(e);
        return toDTO(e);
    }

    @Override
    public EnderecoDTO encontrarPorId(@NotNull @Positive Long id) {
        return toDTO(this.repository.findById(id)
                .orElseThrow(() -> new GenericException(MensagemPersonalizada.ALERTA_ELEMENTO_NAO_ENCONTRADO, Endereco.class.getSimpleName())));
    }

    @Override
    public List<EnderecoDTO> encontrarTodos() {
        return this.repository.findAll()
                .stream()
                .map(EnderecoService::toDTO)
                .collect(Collectors.toList());
    }

    private static EnderecoDTO toDTO(Endereco e) {
        return EnderecoDTO.builder()
                .id(e.getId())
                .cep(e.getCep())
                .rua(e.getRua())
                .bairro(e.getBairro())
                .cidade(e.getCidade())
                .numero(e.getNumero())
                .referencia(e.getReferencia())
                .build();
    }

    private Endereco toEntity(EnderecoInsertDTO dto) {
        return Endereco.builder()
                .cidade(dto.getCidade())
                .bairro(dto.getBairro())
                .rua(dto.getRua())
                .numero(dto.getNumero())
                .cep(dto.getCep())
                .referencia(dto.getReferencia())
                .build();
    }
}
