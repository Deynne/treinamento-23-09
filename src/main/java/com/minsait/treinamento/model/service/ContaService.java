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
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.minsait.treinamento.dtos.Transacao.ContaDepositoDTO;
import com.minsait.treinamento.dtos.Transacao.ContaSaqueDTO;
import com.minsait.treinamento.dtos.Transacao.ContaTransferenciaDTO;
import com.minsait.treinamento.dtos.Transacao.ExtratoContaDTO;
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
    @Lazy
    private UsuarioService usuarioService;
    @Autowired
    @Lazy
    private TransacaoService transacaoService;
    
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
       
       transacaoService.novaConta(c);
       
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
                        .bloqueado(c.getBloqueado())
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
        
        if(c.getBloqueado())
            throw new GenericException(MensagemPersonalizada.ERRO_CONTA_BLOQUEADA,
                    Conta.class.getSimpleName());
        
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
        
        if(c.getBloqueado())
            throw new GenericException(MensagemPersonalizada.ERRO_CONTA_BLOQUEADA,
                    Conta.class.getSimpleName());
        
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
    
    public Conta encontrarPorIdEntity(@NotNull @Positive Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new GenericException(MensagemPersonalizada.
                        ALERTA_ELEMENTO_NAO_ENCONTRADO,
                    Conta.class
                        .getSimpleName()));
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


    @Transactional
    public ContaDTO deposito(ContaDepositoDTO dto) {
        Conta c = this.repository.achaPorAgenciaEConta(dto.getAgencia(), dto.getConta())
                .orElseThrow(() -> new GenericException(MensagemPersonalizada.ERRO_CONTA_INVALIDA,
                                                         Conta.class.getSimpleName()));
        
        if(c.getBloqueado() || c.getUsuario().getBloqueado())
            throw new GenericException(MensagemPersonalizada.ERRO_CONTA_BLOQUEADA,
                    Conta.class.getSimpleName());
        
        c.setSaldo(c.getSaldo() + dto.getValor());        
        this.repository.save(c);        
        transacaoService.deposito(c, dto.getValor());

        return toDTO(c);
    }
    
    
    @Transactional
    public ContaDTO saque(ContaSaqueDTO dto) {
        Conta c = this.repository.achaPorAgenciaEConta(dto.getAgencia(), dto.getConta())
                .orElseThrow(() -> new GenericException(MensagemPersonalizada.ERRO_CONTA_INVALIDA,
                                                        Conta.class.getSimpleName()));
        
        if(c.getBloqueado() || c.getUsuario().getBloqueado())
            throw new GenericException(MensagemPersonalizada.ERRO_CONTA_BLOQUEADA,
                    Conta.class.getSimpleName());
        
        if(c.getSaldo() < dto.getValor())
            throw new GenericException(MensagemPersonalizada.ALERTA_SALDO_INSUFICIENTE,
                    Conta.class.getSimpleName());
        
        c.setSaldo(c.getSaldo() - dto.getValor());
        this.repository.save(c);        
        transacaoService.saque(c, dto.getValor());
        
        return toDTO(c);
    }


    @Transactional
    public ContaDTO transferencia(@Valid ContaTransferenciaDTO dto) {
        var co = this.repository.achaPorAgenciaEConta(dto.getAgenciaOrigem(), dto.getContaOrigem())
                .orElseThrow(() -> new GenericException(MensagemPersonalizada.ERRO_CONTA_ORIGEM_INVALIDA,
                                                        Conta.class.getSimpleName()));
        
        var cd = this.repository.achaPorAgenciaEConta(dto.getAgenciaDestino(), dto.getContaDestino())
                .orElseThrow(() -> new GenericException(MensagemPersonalizada.ERRO_CONTA_DESTINO_INVALIDA,
                                                        Conta.class.getSimpleName()));
        
        if(co.getBloqueado() || co.getUsuario().getBloqueado()
           || cd.getBloqueado() || cd.getUsuario().getBloqueado())
            throw new GenericException(MensagemPersonalizada.ERRO_CONTA_BLOQUEADA,
                    Conta.class.getSimpleName());
        
        if(co.getSaldo() < dto.getValor())
            throw new GenericException(MensagemPersonalizada.ALERTA_SALDO_INSUFICIENTE,
                    Conta.class.getSimpleName());
        
        co.setSaldo(co.getSaldo() - dto.getValor());
        this.repository.save(co);
        
        cd.setSaldo(cd.getSaldo() + dto.getValor());
        this.repository.save(cd);
        
        transacaoService.transferencia(co, cd, dto.getValor());
        
        return toDTO(co);
    }


    public List<ExtratoContaDTO> extrato(@NotNull @Positive Long id) {
        Conta c = this.repository
                      .findById(id)
                      .orElseThrow(() -> new GenericException(
                                                  MensagemPersonalizada.ALERTA_ELEMENTO_NAO_ENCONTRADO,
                                                  Conta.class.getSimpleName()));
        if(c.getBloqueado())
            throw new GenericException(MensagemPersonalizada.ERRO_CONTA_BLOQUEADA,
                    Conta.class.getSimpleName());
        return transacaoService.encontrarPorConta(c);
    }
    

    public ContaDTO bloqueio(@NotNull @Positive Long id, Boolean bloqueio) {
        var c = this.repository.findById(id)
                    .orElseThrow(() -> new GenericException(MensagemPersonalizada.ALERTA_ELEMENTO_NAO_ENCONTRADO,
                    Conta.class.getSimpleName()));
        c.setBloqueado(bloqueio);
        this.repository.save(c);        
        return toDTO(c);
    }

    
    public int bloqueioPorUsuario(@NotNull @Positive Long id, Boolean bloqueio) {
        var u = usuarioService.encontrarEntidadePorId(id);
        return this.bloquearPorUsuario(u, bloqueio);
    }

    public int bloquearPorUsuario(Usuario u, Boolean bloqueio) {
        return this.repository.bloquearTodasPorUsuario(u, bloqueio);
    }


    public boolean possuiConta(Usuario u) {
        return this.repository.existsByUsuario(u);
    }
    
}
