package com.minsait.treinamento.model.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.minsait.treinamento.dtos.Transacao.ExtratoContaDTO;
import com.minsait.treinamento.dtos.Transacao.ExtratoUsuarioDTO;
import com.minsait.treinamento.exceptions.GenericException;
import com.minsait.treinamento.exceptions.MensagemPersonalizada;
import com.minsait.treinamento.model.entities.Conta;
import com.minsait.treinamento.model.entities.Transacao;
import com.minsait.treinamento.model.entities.Usuario;
import com.minsait.treinamento.model.enumerators.TipoOperacao;
import com.minsait.treinamento.model.repositories.TransacaoRepository;

@Service
public class TransacaoService extends GenericCrudServiceImpl<TransacaoRepository, Long, Boolean, Boolean, ExtratoContaDTO>{
    
    public void novaConta(Conta c) {
        var t = Transacao.builder()
                         .conta(c)
                         .operacao(TipoOperacao.CRIACAO)
                         .valor(c.getSaldo())
                         .build();
        this.repository.save(t);
    }
    
    public void deposito(Conta c, Double valor) {
        var t = Transacao.builder()
                .conta(c)
                .operacao(TipoOperacao.DEPOSITO)
                .valor(valor)
                .build();
        this.repository.save(t);
    }
    
    public void saque(Conta c, Double valor) {
        var t = Transacao.builder()
                .conta(c)
                .operacao(TipoOperacao.SAQUE)
                .valor(valor)
                .build();
        this.repository.save(t);
    }
    
    public void transferencia(Conta origem, Conta destino, Double valor) {
        var te = Transacao.builder()
                .conta(origem)
                .contaOperacao(destino)
                .operacao(TipoOperacao.TRANSFERENCIA_ENVIO)
                .valor(valor)
                .build();
        this.repository.save(te);
        
        var tr = Transacao.builder()
                .conta(destino)
                .contaOperacao(origem)
                .operacao(TipoOperacao.TRANSFERENCIA_RECEBIDA)
                .valor(valor)
                .build();
        this.repository.save(tr);
    }

    @Transactional
    public List<ExtratoUsuarioDTO> encontrarPorUsuario(Usuario u) {        
        return this.repository.acharPorUsuario(u)
                .stream()
                .map(TransacaoService::toUsuarioDTO)
                .collect(Collectors.toList());
    }
 
    @Transactional
    public List<ExtratoContaDTO> encontrarPorConta(Conta c){        
        return this.repository.acharPorConta(c)
                .stream()
                .map(TransacaoService::toContaDTO)
                .collect(Collectors.toList());
    }
    
    public static ExtratoUsuarioDTO toUsuarioDTO(Transacao t) {
        var e = ExtratoUsuarioDTO.builder()
                                .conta(t.getConta().getNumConta())
                                .agencia(t.getConta().getNumAgencia())
                                .data(t.getData())
                                .operacao(t.getOperacao())
                                .valor(t.getValor())
                                .build();
        if(e.getOperacao().equals(TipoOperacao.TRANSFERENCIA_ENVIO) || 
           e.getOperacao().equals(TipoOperacao.TRANSFERENCIA_RECEBIDA)  ) {
                e.setContaOp(t.getContaOperacao().getNumConta());
                e.setAgenciaOp(t.getContaOperacao().getNumAgencia());
        }
        return e;
    }
    
    private static ExtratoContaDTO toContaDTO(Transacao t) {
        var e = ExtratoContaDTO.builder()
                               .data(t.getData())
                               .operacao(t.getOperacao())
                               .valor(t.getValor())
                               .build();
        if(e.getOperacao().equals(TipoOperacao.TRANSFERENCIA_ENVIO) || 
           e.getOperacao().equals(TipoOperacao.TRANSFERENCIA_RECEBIDA)  ) {
                e.setContaOp(t.getContaOperacao().getNumConta());
                e.setAgenciaOp(t.getContaOperacao().getNumAgencia());
        }
        return e;
    }
    
    
    
    @Override
    public ExtratoContaDTO salvar(@Valid Boolean dto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ExtratoContaDTO atualizar(@Valid Boolean dto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ExtratoContaDTO excluir(@NotNull @Positive Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ExtratoContaDTO encontrarPorId(@NotNull @Positive Long id) {
        // TODO Auto-generated method stub
        return null;
    }
    

    @Override
    public List<ExtratoContaDTO> encontrarTodos() {
        // TODO Auto-generated method stub
        return null;
    }

}
