package com.minsait.treinamento.model.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minsait.treinamento.dtos.extrato.ExtratoDTO;
import com.minsait.treinamento.dtos.extrato.ExtratoInsertDTO;
import com.minsait.treinamento.dtos.extrato.ExtratoUpdateDTO;
import com.minsait.treinamento.exceptions.GenericException;
import com.minsait.treinamento.exceptions.MensagemPersonalizada;
import com.minsait.treinamento.model.entities.Conta;
import com.minsait.treinamento.model.entities.Extrato;
import com.minsait.treinamento.model.entities.HistoricoTransacao;
import com.minsait.treinamento.model.repositories.ExtratoRepository;

@Service
public class ExtratoService extends GenericCrudServiceImpl<ExtratoRepository, Long, ExtratoInsertDTO, ExtratoUpdateDTO, ExtratoDTO>{
	
	@Autowired
    private ContaService contaService;
	
	@Override
	public ExtratoDTO salvar(@Valid ExtratoInsertDTO dto) {
		Conta contaVinculada = this.contaService.encontrarEntidadePorId(dto.getIdContaVinculada());
		
		Extrato e = Extrato.builder()
				.contaVinculada(contaVinculada)
				.dataInicio(dto.getDataInicio())
				.dataSolicitacao(LocalDateTime.now())
				.identificador(UUID.randomUUID())
				.build();
		
		e = this.repository.save(e);
		
		return toDTO(e);
	}

	public static ExtratoDTO toDTO(Extrato e) {
		return ExtratoDTO.builder()
				.id(e.getId())
				.contaVinculada(e.getContaVinculada())
				.dataInicio(e.getDataInicio())
				.dataSolicitacao(e.getDataSolicitacao())
				.identificador(e.getIdentificador())
				.build();
	}
	
	@Override
	public ExtratoDTO atualizar(@Valid ExtratoUpdateDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExtratoDTO excluir(@NotNull @Positive Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExtratoDTO encontrarPorId(@NotNull @Positive Long id) {
		return toDTO(this.repository.findById(id)
				.orElseThrow(()->new GenericException(MensagemPersonalizada.ALERTA_ELEMENTO_NAO_ENCONTRADO, 
                        Extrato.class.getSimpleName()) ));
	}
	
	public ExtratoDTO encontrarPorUuid(@NotNull @Positive UUID identificador) {
		return toDTO(this.repository.encontrarExtratoPorUuid(identificador));
				
				
	}

	@Override
	public List<ExtratoDTO> encontrarTodos() {
		return this.repository.findAll().stream().map(ExtratoService::toDTO).collect(Collectors.toList());
	}

}
