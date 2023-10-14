package com.minsait.treinamento.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.minsait.treinamento.controller.rest.ContaRest;
import com.minsait.treinamento.dtos.conta.ContaDTO;
import com.minsait.treinamento.dtos.conta.ContaInsertDTO;
import com.minsait.treinamento.dtos.conta.ContaUpdateDTO;
import com.minsait.treinamento.dtos.conta.DadosContaDTO;
import com.minsait.treinamento.dtos.conta.TransacaoSimplesDTO;
import com.minsait.treinamento.dtos.conta.TransferenciaDTO;
import com.minsait.treinamento.dtos.historicoTransacao.HistoricoTransacaoInsertDTO;
import com.minsait.treinamento.exceptions.GenericException;
import com.minsait.treinamento.exceptions.MensagemPersonalizada;
import com.minsait.treinamento.model.entities.Conta;
import com.minsait.treinamento.model.entities.Usuario;
import com.minsait.treinamento.model.entities.HistoricoTransacao.TipoTransacao;
import com.minsait.treinamento.model.repositories.ContaRepository;

@Service
public class ContaService extends GenericCrudServiceImpl<ContaRepository, Long, ContaInsertDTO, ContaUpdateDTO, ContaDTO> {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    @Lazy
    private HistoricoTransacaoService historicoTransacaoService;
    
    @Override
    public ContaDTO salvar(@Valid ContaInsertDTO dto) {
        Usuario u = this.usuarioService.encontrarEntidadePorId(dto.getIdUsuario());
        
        Conta c = Conta.builder()
                        .numAgencia(dto.getNumAgencia())
                        .numConta(dto.getNumConta())
                        .saldo(0.0)
                        .usuario(u)
                        .bloqueado(false)
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
                        .bloqueado(c.isBloqueado())
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
        
        if(dto.getBloqueado() != null) {
            c.setBloqueado(dto.getBloqueado());
        }
        
        this.repository.save(c);
        return ContaService.toDTO(c);
    }

    @Override
    public ContaDTO excluir(@NotNull @Positive Long id) {
        Conta c = encontrarEntidadePorId(id);
       this.repository.delete(c);
       
       return toDTO(c);
    }

    @Override
    public ContaDTO encontrarPorId(@NotNull @Positive Long id) {
        return toDTO(encontrarEntidadePorId(id));
    }


    public Conta encontrarEntidadePorId(Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new GenericException(MensagemPersonalizada.
                        ALERTA_ELEMENTO_NAO_ENCONTRADO,
                    Conta.class
                        .getSimpleName()));
    }

    @Override
    public List<ContaDTO> encontrarTodos() {
        return this.repository.findAll()
                                .stream()
                                .map(ContaService::toDTO)
                                .collect(Collectors.toList());
    }


    public List<ContaDTO> acharTodasPorUsuario(@NotNull @Positive Long id) {
        Usuario u = this.usuarioService.encontrarEntidadePorId(id);
        
        return this.repository.findAllByUsuarioOrderBySaldoDesc(u)
                              .stream()
                              .map(ContaService::toDTO)
                              .collect(Collectors.toList());
    }
    
    public List<ContaDTO> acharTodasPorUsuarioEValorMinimoOrdemParametro(@NotNull 
                                                                         @Positive 
                                                                         Long id,
                                                                         @PositiveOrZero 
                                                                         @NotNull 
                                                                         Double valorMinimo) {
        Usuario u = this.usuarioService.encontrarEntidadePorId(id);
        
        return this.repository.acharContasComDinheiroOrdemParametro(u,valorMinimo)
                              .stream()
                              .map(ContaService::toDTO)
                              .collect(Collectors.toList());
    }
    

    public List<ContaDTO> acharTodasPorUsuarioEValorNomeParametro(@NotNull 
                                                                  @Positive 
                                                                  Long id,
                                                                  @PositiveOrZero 
                                                                  @NotNull 
                                                                  Double valorMinimo) {
        Usuario u = this.usuarioService.encontrarEntidadePorId(id);
        
        return this.repository.acharContasComDinheiroNomeParametro(u,valorMinimo)
                              .stream()
                              .map(ContaService::toDTO)
                              .collect(Collectors.toList());
    }
    
    public List<ContaDTO> acharTodasPorUsuarioEValorMinimoQueryNativa(@NotNull 
                                                                      @Positive 
                                                                      Long id,
                                                                      @PositiveOrZero 
                                                                      @NotNull 
                                                                      Double valorMinimo) {
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
    
    public List<String> achaContasPorAgencia(@NotBlank @Size(min = 5, max = 5) String numAgencia) {
        return this.repository.achaContasPorAgencia(numAgencia).stream().collect(Collectors.toList());
        
    }
    

    
    public List<ContaDTO> achaContasPorNomeUsuarioQueryNativa(@NotBlank @Size(min = 3, max = 300) String nome) {
        return this.repository.achaContasPorNomeUsuarioQueryNativa(nome)
                .stream()
                .map(ContaService::toDTO)
                .collect(Collectors.toList());
    }

    public void excluirPorIdUsuario(@NotNull @Positive Long idUsuario) {
        List<Conta> cs = this.repository.findAllByIdUsuario(idUsuario);
        
        this.repository.deleteAll(cs);
    }

    @Transactional(rollbackFor = Exception.class)
    public Double deposito(@Valid TransacaoSimplesDTO dados) {
        Conta c = this.repository.findByNumAgenciaAndNumConta(dados.getNumAgencia(),dados.getNumConta())
                .orElseThrow(() -> new GenericException(MensagemPersonalizada.
                        ALERTA_ELEMENTO_NAO_ENCONTRADO,
                    Conta.class
                        .getSimpleName()));
        
        if(!this.operacaoPermitida(c)) {
            throw new GenericException(MensagemPersonalizada.ERRO_BLOQUEIO_DETECTADO);
        }
        
        c.setSaldo(c.getSaldo() + dados.getValor());
        
        this.repository.save(c);
        this.historicoTransacaoService.salvar(HistoricoTransacaoInsertDTO.builder()
                                                                         .idContaPrincipal(c.getId())
                                                                         .tipo(TipoTransacao.DEPOSITO)
                                                                         .valor(dados.getValor())
                                                                         .build());
        return c.getSaldo();
    }
    
    @Transactional(rollbackFor = Exception.class)
    public Double saque(@Valid TransacaoSimplesDTO dados) {
        Conta c = this.repository.findByNumAgenciaAndNumConta(dados.getNumAgencia(),dados.getNumConta())
                .orElseThrow(() -> new GenericException(MensagemPersonalizada.
                        ALERTA_ELEMENTO_NAO_ENCONTRADO,
                    Conta.class
                        .getSimpleName()));
        

        
        if(!this.operacaoPermitida(c)) {
            throw new GenericException(MensagemPersonalizada.ERRO_BLOQUEIO_DETECTADO);
        }
        
        c.setSaldo(c.getSaldo() - dados.getValor());
        
        if(c.getSaldo() < 0) {
            throw new GenericException(MensagemPersonalizada.ERRO_SALDO_FINAL_NEGATIVO);
        }
        
        this.repository.save(c);
        this.historicoTransacaoService.salvar(HistoricoTransacaoInsertDTO.builder()
                                                                            .idContaPrincipal(c.getId())
                                                                            .tipo(TipoTransacao.SAQUE)
                                                                            .valor(dados.getValor() * -1.0)
                                                                            .build());
        
        return c.getSaldo();
    }
    
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Double> transferencia(@Valid TransferenciaDTO dto) {
        Conta origem = this.repository.findByNumAgenciaAndNumConta(dto.getNumAgenciaOrigem(),
                                                                   dto.getNumContaOrigem())
                                            .orElseThrow(() -> new GenericException(MensagemPersonalizada.
                                                                    ALERTA_ELEMENTO_NAO_ENCONTRADO,
                                                                Conta.class
                                                                    .getSimpleName()));
        
        Conta destino = this.repository.findByNumAgenciaAndNumConta(dto.getNumAgenciaDestino(),
                                                                    dto.getNumContaDestino())
                                            .orElseThrow(() -> new GenericException(MensagemPersonalizada.
                                                                        ALERTA_ELEMENTO_NAO_ENCONTRADO,
                                                                    Conta.class
                                                                    .getSimpleName()));
        

        if(!(this.operacaoPermitida(origem) && this.operacaoPermitida(destino))) {
            throw new GenericException(MensagemPersonalizada.ERRO_BLOQUEIO_DETECTADO);
        }
        
        if(!operacaoValida(dto, origem, destino)) {
            throw new GenericException(MensagemPersonalizada.
                    ERRO_TRANSACAO_INVALIDA);
        }
        
        origem.setSaldo(origem.getSaldo() - dto.getValor());
        
        if(origem.getSaldo() < 0) {
            throw new GenericException(MensagemPersonalizada.ERRO_SALDO_FINAL_NEGATIVO);
        }
        
        destino.setSaldo(destino.getSaldo() + dto.getValor());
        
        this.repository.save(origem);
        this.repository.save(destino);
        
        
        this.historicoTransacaoService.salvar(HistoricoTransacaoInsertDTO.builder()
                                                                            .idContaPrincipal(origem.getId())
                                                                            .idContaRelacionada(destino.getId())
                                                                            .tipo(TipoTransacao.TRANSFERENCIA)
                                                                            .valor(dto.getValor()* -1.0)
                                                                            .build());
        
        this.historicoTransacaoService.salvar(HistoricoTransacaoInsertDTO.builder()
                                                                            .idContaPrincipal(destino.getId())
                                                                            .idContaRelacionada(origem.getId())
                                                                            .tipo(TipoTransacao.TRANSFERENCIA)
                                                                            .valor(dto.getValor())
                                                                            .build());
        
        Map<String, Double> resultado = new HashMap<>();
        
        resultado.put("origem", origem.getSaldo());
        resultado.put("destino", destino.getSaldo());
        
        return resultado;
    }


    private boolean operacaoValida(TransferenciaDTO dto, Conta origem, Conta destino) {
        return origem.getUsuario().getId().equals(destino.getUsuario().getId()) || 
               (dto.getCpf() != null && dto.getCpf().equals(destino.getUsuario().getDocumentacao().getCpf()));
    }
    
    
    @Transactional
    public boolean operacaoPermitida(@Valid DadosContaDTO dto) {
        Conta c = this.repository.findByNumAgenciaAndNumConta(dto.getNumAgencia(),dto.getNumConta())
                .orElseThrow(() -> new GenericException(MensagemPersonalizada.
                        ALERTA_ELEMENTO_NAO_ENCONTRADO,
                    Conta.class
                        .getSimpleName()));
        
        return this.operacaoPermitida(c);
        
    }
    
    @Transactional
    public boolean operacaoPermitida(Conta c) {
        return !(c.isBloqueado() || c.getUsuario().isBloqueado());
        
    }
    
    public boolean desbloquear(@NotNull @Positive Long id) {
        return this.atualizar(ContaUpdateDTO.builder().id(id).bloqueado(false).build()).isBloqueado();
    }
    
    public boolean bloquear(@NotNull @Positive Long id) {
        return this.atualizar(ContaUpdateDTO.builder().id(id).bloqueado(true).build()).isBloqueado();
    }
    
    @Transactional(rollbackFor = Exception.class)
    public void bloquearPorUsuario(Long idUsuario) {
        List<Conta> cs = this.repository.findAllByIdUsuario(idUsuario);
        
        cs.forEach(c -> c.setBloqueado(true));
        
        this.repository.saveAll(cs);
        
    }

    @Transactional(rollbackFor = Exception.class)
    public void desbloquearPorUsuario(Long idUsuario) {
        List<Conta> cs = this.repository.findAllByIdUsuario(idUsuario);
        
        cs.forEach(c -> c.setBloqueado(false));
        
        this.repository.saveAll(cs);
    }


    public ContaDTO getContaPorJDBCEntityManager(Long idUsuario, String numAgencia, String numConta) {
        Conta c = this.repository.acharPorUsuarioOuNumAgenciaEContaEntityManager(idUsuario, numAgencia, numConta);
        return toDTO(c);
    }
    public ContaDTO getContaPorJDBCTemplace(Long idUsuario, String numAgencia, String numConta) {
        Conta c = this.repository.acharPorUsuarioOuNumAgenciaEContaJDBCTemplate(idUsuario, numAgencia, numConta);
        return toDTO(c);
    }
}
