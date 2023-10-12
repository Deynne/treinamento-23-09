package com.minsait.treinamento.model.service;

import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.minsait.treinamento.dtos.Transacao.ExtratoContaDTO;
import com.minsait.treinamento.dtos.Transacao.ExtratoContaPorDataDTO;
import com.minsait.treinamento.exceptions.GenericException;
import com.minsait.treinamento.exceptions.MensagemPersonalizada;
import com.minsait.treinamento.model.entities.Conta;
import com.minsait.treinamento.model.entities.SolicExtrato;
import com.minsait.treinamento.model.repositories.ExtratoRepository;

@Service
public class ExtratoService extends GenericCrudServiceImpl<ExtratoRepository, Long, Boolean, Boolean, ExtratoContaPorDataDTO>{

    @Autowired
    @Lazy
    private ContaService contaService;
    @Autowired
    @Lazy
    private TransacaoService transacaoService;
    
    @Transactional
    public ExtratoContaPorDataDTO extratoPorData(Long id, Date aPartirDe) {
        
        Conta c = this.contaService.encontrarPorIdEntity(id);
        
        if(c.getBloqueado() || c.getUsuario().getBloqueado())
            throw new GenericException(MensagemPersonalizada.ERRO_CONTA_BLOQUEADA,
                    Conta.class.getSimpleName());
                
        var e = SolicExtrato.builder()
                            .conta(c)
                            .aPartirDe(aPartirDe)
                            .build();
        this.repository.save(e);
        
        var t = this.transacaoService.encontrarPorContaEDatas(c, aPartirDe, new Date());
        
        return this.toExtratoPorDataDTO(e, t);
    }
    
    @Transactional
    public ExtratoContaPorDataDTO extratoPorVerificador(String uuid) {          
        
        var e = this.repository.findByVerificador(uuid)
                .orElseThrow(() -> new GenericException(MensagemPersonalizada.ERRO_EXTRATO_NAO_EXISTE,
                                                        Conta.class.getSimpleName()));
        
        var t = this.transacaoService.encontrarPorContaEDatas(e.getConta(), e.getAPartirDe(), e.getDataSolic());
        
        return this.toExtratoPorDataDTO(e, t);
    } 

    public Boolean validaExtrato(String uuid) {
        this.repository.findByVerificador(uuid)
                .orElseThrow(() -> new GenericException(MensagemPersonalizada.ERRO_EXTRATO_NAO_EXISTE,
                                                        Conta.class.getSimpleName()));
        return true;
    }
    
    private ExtratoContaPorDataDTO toExtratoPorDataDTO(SolicExtrato e, List<ExtratoContaDTO> t) {
        return ExtratoContaPorDataDTO.builder()
                .conta(contaService.encontrarPorId(e.getConta().getId()))
                .dataSolicitado(e.getDataSolic())
                .aPartirDe(e.getAPartirDe())
                .verificador(e.getVerificador().toString())
                .transacoes(t)
                .build();
    }
    
    
    // Default Functions
    
    @Override
    public ExtratoContaPorDataDTO salvar(@Valid Boolean dto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ExtratoContaPorDataDTO atualizar(@Valid Boolean dto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ExtratoContaPorDataDTO excluir(@NotNull @Positive Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ExtratoContaPorDataDTO encontrarPorId(@NotNull @Positive Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ExtratoContaPorDataDTO> encontrarTodos() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
