package com.minsait.treinamento.model.service;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.stereotype.Service;

import com.minsait.treinamento.dtos.usuario.UsuarioDTO;
import com.minsait.treinamento.dtos.usuario.UsuarioInsertDTO;
import com.minsait.treinamento.dtos.usuario.UsuarioUpdateDTO;
import com.minsait.treinamento.exceptions.GenericException;
import com.minsait.treinamento.exceptions.MensagemPersonalizada;
import com.minsait.treinamento.model.entities.Usuario;
import com.minsait.treinamento.model.repositories.UsuarioRepository;

@Service
public class UsuarioService extends GenericCrudServiceImpl<UsuarioRepository, Long, UsuarioInsertDTO, UsuarioUpdateDTO, UsuarioDTO> {

    @Override
    public UsuarioDTO salvar(UsuarioInsertDTO dto) {
        Usuario u = Usuario.builder()
                            .nome(dto.getNome())
                            .build();
        
        u = this.repository.save(u);
        
        return toDTO(u);
    }
    


    @Override
    public UsuarioDTO atualizar(UsuarioUpdateDTO dto) {
        Usuario u = this.repository.findById(dto.getId())
                                    .orElseThrow(() -> 
                                        new GenericException(MensagemPersonalizada.
                                                                ALERTA_ELEMENTO_NAO_ENCONTRADO,
                                                            Usuario.class
                                                                .getSimpleName()));
        
        if(dto.getNome() != null) {
            u.setNome(dto.getNome());
        }
        
        this.repository.save(u);
        
        return toDTO(u);
    }

    
    @Override
    public UsuarioDTO excluir(@NotNull @Positive Long id) {
        Usuario u = this.repository.findById(id)
                /**
                 * Quando passamos uma função anonima para o orElseThrow
                 * isto equivale a criar esta função Supplier
                 */
                .orElseThrow(new Supplier<GenericException>() {
                                @Override
                                public GenericException get() {
                                    return new GenericException(MensagemPersonalizada.
                                                                    ALERTA_ELEMENTO_NAO_ENCONTRADO,
                                                                Usuario.class
                                                                    .getSimpleName());
                                }
                            });
        
        this.repository.delete(u);
        
        return toDTO(u);
    }

    @Override
    public UsuarioDTO encontrarPorId(@NotNull @Positive Long id) {
        return toDTO(this.repository.findById(id)
                    .orElseThrow(() -> {
                            return new GenericException(MensagemPersonalizada.
                                                            ALERTA_ELEMENTO_NAO_ENCONTRADO,
                                                        Usuario.class
                                                            .getSimpleName());
                    }));
    }

    @Override
    public List<UsuarioDTO> encontrarTodos() {
        return this.repository.findAll()
                                .stream()
                                .map(UsuarioService::toDTO)
                                .collect(Collectors.toList());
    }

    public static UsuarioDTO toDTO(@NotNull Usuario u) {
        return UsuarioDTO.builder()
                            .id(u.getId())
                            .nome(u.getNome())
                            .build();
    }

}
