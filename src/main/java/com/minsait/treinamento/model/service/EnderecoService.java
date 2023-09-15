package com.minsait.treinamento.model.service;

import com.minsait.treinamento.dtos.endereco.EnderecoDTO;
import com.minsait.treinamento.dtos.endereco.EnderecoInsertDTO;
import com.minsait.treinamento.dtos.endereco.EnderecoUpdateDTO;
import com.minsait.treinamento.exceptions.GenericException;
import com.minsait.treinamento.exceptions.MensagemPersonalizada;
import com.minsait.treinamento.model.entities.Endereco;
import com.minsait.treinamento.model.entities.Usuario;
import com.minsait.treinamento.model.repositories.EnderecoRepository;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class EnderecoService extends GenericCrudServiceImpl<EnderecoRepository, Long, EnderecoInsertDTO, EnderecoUpdateDTO, EnderecoDTO> {

    @Override
    public EnderecoDTO salvar(EnderecoInsertDTO dto) {
        Endereco e = Endereco.builder()
                            .cidade(dto.getCidade())
                            .bairro(dto.getBairro())
                            .cep(dto.getCep())
                            .rua(dto.getRua())
                            .numero(dto.getNumero())
                            .referencia(dto.getReferencia())
                            .build();
        
        e = this.repository.save(e);
        
        return toDTO(e);
    }
    


    @Override
    public EnderecoDTO atualizar(EnderecoUpdateDTO dto) {
        Endereco u = this.repository.findById(dto.getId())
                                    .orElseThrow(() -> 
                                        new GenericException(MensagemPersonalizada.
                                                                ALERTA_ELEMENTO_NAO_ENCONTRADO,
                                                            Endereco.class
                                                                .getSimpleName()));
        
        if(dto.getCidade() != null) {
            u.setCidade(dto.getCidade());
        }
        if(dto.getBairro() != null) {
            u.setBairro(dto.getBairro());
        }
        if(dto.getCep() != null) {
            u.setCep(dto.getCep());
        }
        if(dto.getRua() != null) {
            u.setRua(dto.getRua());
        }
        if(dto.getNumero() != null) {
            u.setNumero(dto.getNumero());
        }
        if(dto.getReferencia() != null) {
            u.setReferencia(dto.getReferencia());
        }

        this.repository.save(u);
        
        return toDTO(u);
    }

    
    @Override
    public EnderecoDTO excluir(@NotNull @Positive Long id) {
        Endereco u = this.repository.findById(id)
                /**
                 * Quando passamos uma função anonima para o orElseThrow
                 * isto equivale a criar esta função Supplier
                 */
                .orElseThrow(new Supplier<GenericException>() {
                                @Override
                                public GenericException get() {
                                    return new GenericException(MensagemPersonalizada.
                                                                    ALERTA_ELEMENTO_NAO_ENCONTRADO,
                                                                Endereco.class
                                                                    .getSimpleName());
                                }
                            });
        
        this.repository.delete(u);
        
        return toDTO(u);
    }

    @Override
    public EnderecoDTO encontrarPorId(@NotNull @Positive Long id) {
        return toDTO(this.repository.findById(id)
                    .orElseThrow(() -> {
                            return new GenericException(MensagemPersonalizada.
                                                            ALERTA_ELEMENTO_NAO_ENCONTRADO,
                                                        Endereco.class
                                                            .getSimpleName());
                    }));
    }

    @Override
    public List<EnderecoDTO> encontrarTodos() {
        return this.repository.findAll()
                                .stream()
                                .map(EnderecoService::toDTO)
                                .collect(Collectors.toList());
    }

    public static EnderecoDTO toDTO(@NotNull Endereco u) {
        return EnderecoDTO.builder()
                            .id(u.getId())
                            .cidade(u.getCidade())
                            .bairro(u.getBairro())
                            .cep(u.getCep())
                            .rua(u.getRua())
                            .numero(u.getNumero())
                            .referencia(u.getReferencia())
                            .build();
    }

}
