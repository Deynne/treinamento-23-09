package com.minsait.treinamento.model.service;

import com.minsait.treinamento.dtos.conta.DadosContaDTO;
import com.minsait.treinamento.dtos.historicoTransacao.HistoricoTransacaoDTO;
import com.minsait.treinamento.dtos.historicoTransacao.HistoricoTransacaoInsertDTO;
import com.minsait.treinamento.dtos.historicoTransacao.HistoricoTransacaoUpdateDTO;
import com.minsait.treinamento.exceptions.GenericException;
import com.minsait.treinamento.exceptions.MensagemPersonalizada;
import com.minsait.treinamento.model.entities.Conta;
import com.minsait.treinamento.model.entities.HistoricoExtrato;
import com.minsait.treinamento.model.entities.HistoricoTransacao;
import com.minsait.treinamento.model.repositories.HistoricoTransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HistoricoTransacaoService extends GenericCrudServiceImpl<HistoricoTransacaoRepository, Long, HistoricoTransacaoInsertDTO, HistoricoTransacaoUpdateDTO, HistoricoTransacaoDTO> {

    @Autowired
    private ContaService contaService;

    @Autowired
    private HistoricoExtratoService extratoService;

    @Override
    public HistoricoTransacaoDTO salvar(@Valid HistoricoTransacaoInsertDTO dto) {
        Conta contaPrincipal = this.contaService.encontrarEntidadePorId(dto.getIdContaPrincipal());
        Conta contaRelacionada = null;
        if (dto.getIdContaRelacionada() != null) {
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

        if (dto.getIdContaPrincipal() != null) {
            Conta contaPrincipal = this.contaService.encontrarEntidadePorId(dto.getIdContaPrincipal());
            h.setContaPrincipal(contaPrincipal);
        }

        if (dto.getIdContaRelacionada() != null) {
            Conta contaRelacionada = this.contaService.encontrarEntidadePorId(dto.getIdContaRelacionada());
            h.setContaRelacionada(contaRelacionada);
        }


        if (dto.getTipo() != null) {
            h.setTipo(dto.getTipo());
        }

        if (dto.getValor() != null) {
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
        if (!this.contaService.operacaoPermitida(dto)) {
            throw new GenericException(MensagemPersonalizada.ERRO_BLOQUEIO_DETECTADO);
        }
        List<HistoricoTransacao> extrato = this.repository.findAllByAgenciaAndConta(dto.getNumAgencia(), dto.getNumConta());

        return extrato.stream().map(HistoricoTransacaoService::toDTO).collect(Collectors.toList());
    }

    public List<HistoricoTransacaoDTO> buscarExtratoPorDataEdpecifica(Long id, String endDate) {
        LocalDate localDate = LocalDate.parse(endDate);
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = localDate.atTime(LocalTime.MIDNIGHT);
        return getHistoricoTransacaoDTOS(id, start, end);
    }

    public List<HistoricoTransacaoDTO> buscarExtratoPorMesesAteriores(Long id, Integer mes) {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.minusMonths(mes).with(LocalTime.MIDNIGHT);
        return getHistoricoTransacaoDTOS(id, start, end);
    }

    public List<HistoricoTransacaoDTO> buscarExtratoPorDiasAteriores(Long id, Integer dia) {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.minusDays(dia).with(LocalTime.MIDNIGHT);
        return getHistoricoTransacaoDTOS(id, start, end);
    }

    public List<HistoricoTransacaoDTO> getHistoricoTransacaoDTOS(Long id, LocalDateTime end, LocalDateTime start) {
        Conta conta = contaService.encontrarEntidadePorId(id);

        List<HistoricoTransacao> transacaoList = this.repository
                .findByTimestampBetweenAndContaPrincipal_Id(start, end, conta.getId());

        HistoricoExtrato extrato = HistoricoExtrato.builder()
                .dataInicial(start)
                .dataFinal(end)
                .idContaOrigem(conta)
                .build();

        extratoService.salvar(extrato);

        return transacaoList.stream().map(HistoricoTransacaoService::toDTO).collect(Collectors.toList());
    }

    public List<HistoricoTransacaoDTO> gerarExtratoPorCodigo(UUID codigo) {
        HistoricoExtrato h = extratoService.encontrarPorCodigo(codigo);

        List<HistoricoTransacao> transacaoList = this.repository
                .findByTimestampBetweenAndContaPrincipal_Id(
                        h.getDataInicial(), h.getDataFinal(), h.getIdContaOrigem().getId());

        return transacaoList.stream().map(HistoricoTransacaoService::toDTO).collect(Collectors.toList());
    }

}
