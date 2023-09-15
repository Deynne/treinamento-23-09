package com.minsait.treinamento.model.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.minsait.treinamento.dtos.IdentificadorBasicoDTO;
import com.minsait.treinamento.dtos.conta.ContaDTO;
import com.minsait.treinamento.dtos.conta.ContaInsertDTO;
import com.minsait.treinamento.dtos.conta.ContaUpdateDTO;
import com.minsait.treinamento.exceptions.GenericException;
import com.minsait.treinamento.exceptions.MensagemPersonalizada;
import com.minsait.treinamento.model.entities.Conta;
import com.minsait.treinamento.model.entities.Usuario;
import com.minsait.treinamento.model.repositories.ContaRepository;

@Service
public class ContaService extends GenericCrudServiceImpl<ContaRepository, Long, ContaInsertDTO, ContaUpdateDTO, ContaDTO> {

    @Autowired
    private UsuarioService usuarioService;
    
    @Override
    public ContaDTO salvar(@Valid ContaInsertDTO dto) {
        Usuario u = this.usuarioService.encontrarEntidadePorId(dto.getIdUsuario());
        
        Conta c = Conta.builder()
                        .numAgencia(dto.getNumAgencia())
                        .numConta(dto.getNumConta())
                        .saldo(0.0)
                        .usuario(u)
                        .build();
                        
       c = this.repository.save(c);
       
       return ContaService.toDTO(c);
    }


    @Transactional
    public static ContaDTO toDTO(Conta c) {
        return ContaDTO.builder()
                        .idUsuario(c.getUsuario().getId())
                        .id(c.getId())
                        .numAgencia(c.getNumAgencia())
                        .numConta(c.getNumConta())
//                        .usuario(IdentificadorBasicoDTO.
//                                    <Long>builder()
//                                    .id(c.getUsuario().getId())
//                                    .nome(c.getUsuario().getNome())
//                                    .build())
                        .saldo(c.getSaldo())
                        .build();
    }

    @Override
    @Transactional
    public ContaDTO atualizar(@Valid ContaUpdateDTO dto) {
        Conta c = this.repository.findById(dto.getId())
                .orElseThrow(() -> new GenericException(MensagemPersonalizada.
                                                    ALERTA_ELEMENTO_NAO_ENCONTRADO,
                                                Conta.class
                                                    .getSimpleName()));
        if(dto.getNumAgencia() != null) {
            c.setNumAgencia(dto.getNumAgencia());
        }
        
        if(dto.getNumConta() != null) {
            c.setNumConta(dto.getNumConta());
        }
        
        if(dto.getSaldo() != null) {
            c.setSaldo(dto.getSaldo());
        }
        
        if(dto.getIdUsuario() != null) {
            Usuario u = this.usuarioService.encontrarEntidadePorId(dto.getIdUsuario());
            
            if(!u.getId().equals(c.getUsuario().getId())) {
                c.setUsuario(u);
            }
        }
        
        this.repository.save(c);
        return ContaService.toDTO(c);
    }

    @Override
    public ContaDTO excluir(@NotNull @Positive Long id) {
        Conta c = this.repository.findById(id)
                .orElseThrow(() -> new GenericException(MensagemPersonalizada.
                        ALERTA_ELEMENTO_NAO_ENCONTRADO,
                    Conta.class
                        .getSimpleName()));
       this.repository.delete(c);
       
       return toDTO(c);
    }

    @Override
    public ContaDTO encontrarPorId(@NotNull @Positive Long id) {
        return toDTO(this.repository.findById(id)
                .orElseThrow(() -> new GenericException(MensagemPersonalizada.
                        ALERTA_ELEMENTO_NAO_ENCONTRADO,
                    Conta.class
                        .getSimpleName())));
    }

    @Override
    public List<ContaDTO> encontrarTodos() {
        return this.repository.findAll().stream().map(ContaService::toDTO).collect(Collectors.toList());
    }


    public List<ContaDTO> acharTodasPorUsuario(@NotNull @Positive Long id) {
        Usuario u = this.usuarioService.encontrarEntidadePorId(id);
        
        return this.repository.findAllByUsuarioOrderBySaldoDesc(u)
                              .stream()
                              .map(ContaService::toDTO)
                              .collect(Collectors.toList());
    }
    
    public List<ContaDTO> acharTodasPorUsuarioEValorMinimoOrdemParametro(@NotNull @Positive Long id,@PositiveOrZero @NotNull Double valorMinimo) {
        Usuario u = this.usuarioService.encontrarEntidadePorId(id);
        
        return this.repository.acharContasComDinheiroOrdemParametro(u,valorMinimo)
                              .stream()
                              .map(ContaService::toDTO)
                              .collect(Collectors.toList());
    }
    

    public List<ContaDTO> acharTodasPorUsuarioEValorNomeParametro(@NotNull @Positive Long id,@PositiveOrZero @NotNull Double valorMinimo) {
        Usuario u = this.usuarioService.encontrarEntidadePorId(id);
        
        return this.repository.acharContasComDinheiroNomeParametro(u,valorMinimo)
                              .stream()
                              .map(ContaService::toDTO)
                              .collect(Collectors.toList());
    }
    
    public List<ContaDTO> acharTodasPorUsuarioEValorMinimoQueryNativa(@NotNull @Positive Long id,@PositiveOrZero @NotNull Double valorMinimo) {
        Usuario u = this.usuarioService.encontrarEntidadePorId(id);
        
        return this.repository.acharContasComDinheiroQueryNativa(u,valorMinimo)
                              .stream()
                              .map(ContaService::toDTO)
                              .collect(Collectors.toList());
    }


    public List<ContaDTO> achaContasPorNomeUsuario(@NotBlank @Size(min = 3, max = 300) String nome) {
        return this.repository.achaContasPorNomeUsuario(nome)
                .stream()
                .map(ContaService::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<ContaDTO> achaContasPorNomeUsuarioQueryNativa(@NotBlank @Size(min = 3, max = 300) String nome) {
        return this.repository.achaContasPorNomeUsuarioQueryNativa(nome)
                .stream()
                .map(ContaService::toDTO)
                .collect(Collectors.toList());
    }

}
