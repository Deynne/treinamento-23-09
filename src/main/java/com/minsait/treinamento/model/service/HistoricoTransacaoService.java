package com.minsait.treinamento.model.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minsait.treinamento.dtos.conta.DadosContaDTO;
import com.minsait.treinamento.dtos.historicoTransacao.HistoricoTransacaoDTO;
import com.minsait.treinamento.dtos.historicoTransacao.HistoricoTransacaoInsertDTO;
import com.minsait.treinamento.dtos.historicoTransacao.HistoricoTransacaoUpdateDTO;
import com.minsait.treinamento.exceptions.GenericException;
import com.minsait.treinamento.exceptions.MensagemPersonalizada;
import com.minsait.treinamento.model.entities.Conta;
import com.minsait.treinamento.model.entities.HistoricoTransacao;
import com.minsait.treinamento.model.entities.HistoricoTransacao.TipoTransacao;
import com.minsait.treinamento.model.repositories.HistoricoTransacaoRepository;

@Service
public class HistoricoTransacaoService extends GenericCrudServiceImpl<HistoricoTransacaoRepository, Long, HistoricoTransacaoInsertDTO, HistoricoTransacaoUpdateDTO, HistoricoTransacaoDTO> {

    @Autowired
    private ContaService contaService;
    
    @Override
    public HistoricoTransacaoDTO salvar(@Valid HistoricoTransacaoInsertDTO dto) {
        Conta contaPrincipal = this.contaService.encontrarEntidadePorId(dto.getIdContaPrincipal());
        Conta contaRelacionada = null;
        if(dto.getIdContaRelacionada() != null) {
            contaRelacionada = this.contaService.encontrarEntidadePorId(dto.getIdContaRelacionada());
        }
        
        HistoricoTransacao h = HistoricoTransacao.builder()
                                                 .contaRelacionada(contaRelacionada)
                                                 .contaPrincipal(contaPrincipal)
                                                 .timestamp(LocalDateTime.now())
                                                 .tipo(dto.getTipo())
                                                 .valor(dto.getValor())
                                                 .build();
        
        h = this.repository.save(h);
        
        return toDTO(h);
    }

    public static HistoricoTransacaoDTO toDTO(HistoricoTransacao h) {
        return HistoricoTransacaoDTO.builder()
                                    .id(h.getId())
                                    .idContaRelacionada(h.getContaRelacionada() != null ? h.getContaRelacionada().getId() : null)
                                    .idContaPrincipal(h.getContaPrincipal().getId())
                                    .timestamp(h.getTimestamp())
                                    .tipo(h.getTipo())
                                    .valor(h.getValor())
                                    .build();
    }

    @Override
    public HistoricoTransacaoDTO atualizar(@Valid HistoricoTransacaoUpdateDTO dto) {
        HistoricoTransacao h = this.repository.findById(dto.getId())
                                    .orElseThrow(() -> new GenericException(MensagemPersonalizada.ALERTA_ELEMENTO_NAO_ENCONTRADO, 
                                                                            HistoricoTransacao.class.getSimpleName()));
        
        if(dto.getIdContaPrincipal() != null) {
            Conta contaPrincipal = this.contaService.encontrarEntidadePorId(dto.getIdContaPrincipal());
            h.setContaPrincipal(contaPrincipal);
        }

        if(dto.getIdContaRelacionada() != null) {
            Conta contaRelacionada= this.contaService.encontrarEntidadePorId(dto.getIdContaRelacionada());
            h.setContaRelacionada(contaRelacionada);
        }
        
        
        if(dto.getTipo() != null) {
            h.setTipo(dto.getTipo());
        }
        
        if(dto.getValor() != null) {
            h.setValor(dto.getValor());
        }
        
        this.repository.save(h);
        
        return toDTO(h);
    }

    @Override
    public HistoricoTransacaoDTO excluir(@NotNull @Positive Long id) {
        HistoricoTransacao h = this.repository.findById(id)
                .orElseThrow(() -> new GenericException(MensagemPersonalizada.ALERTA_ELEMENTO_NAO_ENCONTRADO, 
                                                        HistoricoTransacao.class.getSimpleName()));
        
        this.repository.delete(h);
        return toDTO(h);
    }

    @Override
    public HistoricoTransacaoDTO encontrarPorId(@NotNull @Positive Long id) {
        return toDTO(this.repository.findById(id)
                .orElseThrow(() -> new GenericException(MensagemPersonalizada.ALERTA_ELEMENTO_NAO_ENCONTRADO, 
                                                        HistoricoTransacao.class.getSimpleName())));
    }

    @Override
    public List<HistoricoTransacaoDTO> encontrarTodos() {
        return this.repository.findAll().stream().map(HistoricoTransacaoService::toDTO).collect(Collectors.toList());
    }

    public List<HistoricoTransacaoDTO> getExtratoConta(@Valid DadosContaDTO dto) {
        List<HistoricoTransacao> extrato = this.repository.findAllByAgenciaAndConta(dto.getNumAgencia(),dto.getNumConta());
        
        return extrato.stream().map(HistoricoTransacaoService::toDTO).collect(Collectors.toList());
    }
}
