package com.minsait.treinamento.model.service;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.stereotype.Service;

import com.minsait.treinamento.dtos.endereço.EndereçoDTO;
import com.minsait.treinamento.dtos.endereço.EndereçoInsertDTO;
import com.minsait.treinamento.dtos.endereço.EndereçoUpdateDTO;
import com.minsait.treinamento.exceptions.GenericException;
import com.minsait.treinamento.exceptions.MensagemPersonalizada;
import com.minsait.treinamento.model.entities.Endereço;
import com.minsait.treinamento.model.repositories.EndereçoRepository;

@Service
public class EndereçoService extends GenericCrudServiceImpl<EndereçoRepository, Long, EndereçoInsertDTO, EndereçoUpdateDTO, EndereçoDTO> {

	@Override
	public EndereçoDTO salvar(@Valid EndereçoInsertDTO dto) {
		Endereço e = Endereço.builder()
				             .cidade(dto.getCidade())
				             .bairro(dto.getBairro())
				             .rua(dto.getRua())
				             .numero(dto.getNumero())
				             .cep(dto.getCep())
				             .referencia(dto.getReferencia())
				             .build();
		
				   e = this.repository.save(e);
		return toDTO(e);
		
	}
	
	
	@Override
	public EndereçoDTO atualizar(@Valid EndereçoUpdateDTO dto) {
		Endereço e = this.repository.findById(dto.getId())
				.orElseThrow (() ->
		           new GenericException(MensagemPersonalizada.
		        		   ALERTA_ELEMENTO_NAO_ENCONTRADO, Endereço.class.getSimpleName()));
		
		if(dto.getCidade() != null) {
			e.setCidade(dto.getCidade());
		}
		
		if(dto.getBairro() != null) {
			e.setBairro(dto.getBairro());
		}
		
		if(dto.getRua() != null) {
			e.setRua(dto.getRua());
		}
		
		if(dto.getNumero() != 0) {
			e.setNumero(dto.getNumero());
		}
		
		if(dto.getCep() != 0) {
			e.setCep(dto.getCep());
		}
		
		if(dto.getReferencia() != null) {
			e.setReferencia(dto.getReferencia());
		}

		
		this.repository.save(e);
		return toDTO(e);		
	}
	
	
	@Override
	public EndereçoDTO excluir(@NotNull @Positive Long id) {
		Endereço e = this.repository.findById(id)
				.orElseThrow(new Supplier<GenericException>() {
					@Override
					public GenericException get() {
							return new GenericException(MensagemPersonalizada.ALERTA_ELEMENTO_NAO_ENCONTRADO,
									Endereço.class
									.getSimpleName());
						}
				});
		this.repository.delete(e);
		return toDTO(e);
	}

	
	@Override
	public EndereçoDTO encontrarPorId(@NotNull @Positive Long id) {
		return toDTO(this.repository.findById(id)
				.orElseThrow(()-> {
					return new GenericException(MensagemPersonalizada.
							ALERTA_ELEMENTO_NAO_ENCONTRADO,
							Endereço.class
							.getSimpleName());
				}));
	}
	
	
	@Override
	public List<EndereçoDTO> encontrarTodos() {
		return this.repository.findAll()
				              .stream()
				              .map(EndereçoService::toDTO)
				              .collect(Collectors.toList());
	}
	
	
	public static EndereçoDTO toDTO(@NotNull Endereço e) {
		return EndereçoDTO.builder()
						  .cidade(e.getCidade())
			              .bairro(e.getBairro())
			              .rua(e.getRua())
			              .numero(e.getNumero())
			              .cep(e.getCep())
			              .referencia(e.getReferencia())
			              .build();				
	}

}
