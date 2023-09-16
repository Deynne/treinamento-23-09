package com.minsait.treinamento.model.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minsait.treinamento.dtos.conta.ContaDTO;
import com.minsait.treinamento.dtos.endereco.EnderecoDTO;
import com.minsait.treinamento.dtos.endereco.EnderecoInsertDTO;
import com.minsait.treinamento.dtos.endereco.EnderecoUpdateDTO;
import com.minsait.treinamento.exceptions.GenericException;
import com.minsait.treinamento.exceptions.MensagemPersonalizada;
import com.minsait.treinamento.model.entities.Conta;
import com.minsait.treinamento.model.entities.Endereco;
import com.minsait.treinamento.model.entities.Usuario;
import com.minsait.treinamento.model.repositories.EnderecoRepository;

@Service
public class EnderecoService extends GenericCrudServiceImpl<EnderecoRepository, Long, EnderecoInsertDTO, EnderecoUpdateDTO, EnderecoDTO>{

	@Autowired
    private UsuarioService usuarioService;
	
	@Override
	public EnderecoDTO salvar(@Valid EnderecoInsertDTO dto) {
		 Usuario u = this.usuarioService.encontrarEntidadePorId(dto.getIdUsuario());
	        
	        Endereco c = Endereco.builder()
	                        .cidade(dto.getCidade())
	                        .bairro(dto.getBairro())
	                        .cep(dto.getCep())
	                        .rua(dto.getRua())
	                        .referencia(dto.getReferencia())
	                        .numero(dto.getNumero())
	                        .usuario(u)
	                        .build();
	                        
	       c = this.repository.save(c);
	       
	       return EnderecoService.toDTO(c);
	}

	private static EnderecoDTO toDTO(Endereco c) {
		 return EnderecoDTO.builder()
                 .idUsuario(c.getUsuario().getId())
                 .id(c.getId())
                 .cidade(c.getCidade())
                 .bairro(c.getBairro())
                 .rua(c.getRua())
                 .numero(c.getNumero())
                 .cep(c.getCep())
                 .referencia(c.getReferencia())
                 .build();
	}
	
    public List<EnderecoDTO> achaEnderecoPorCepQueryNativa(@NotBlank @Size(min = 9, max = 9) String cep) {
        return this.repository.achaEnderecoPorCepQueryNativa(cep)
                .stream()
                .map(EnderecoService::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EnderecoDTO atualizar(@Valid EnderecoUpdateDTO dto) {
        Endereco c = this.repository.findById(dto.getId()).orElseThrow(() -> new GenericException(MensagemPersonalizada.
                ALERTA_ELEMENTO_NAO_ENCONTRADO,
                Conta.class
                .getSimpleName()));
        
        if(dto.getBairro() != null) {
        	 c.setBairro(dto.getBairro());
        }
        if(dto.getCep() != null) {
        	c.setCep(dto.getCep());
        }
        if(dto.getCidade() != null) {
        	c.setCidade(dto.getCidade());
        }
        if(dto.getNumero() != 0) {
        	c.setNumero(dto.getNumero());
        }
        if(dto.getReferencia() != null) {
        	c.setReferencia(dto.getReferencia());
        }
        if(dto.getRua() != null) {
        	c.setRua(dto.getRua());
        }
        

        c = this.repository.save(c);

        return EnderecoService.toDTO(c);
    }

	@Override
	public EnderecoDTO excluir(@NotNull @Positive Long id) {
		Endereco c = this.repository.findById(id).orElseThrow(() -> new GenericException(MensagemPersonalizada.
                ALERTA_ELEMENTO_NAO_ENCONTRADO,
                Conta.class
                .getSimpleName()));

	    this.repository.delete(c);

	    return EnderecoService.toDTO(c);
	}

	@Override
	public EnderecoDTO encontrarPorId(@NotNull @Positive Long id) {
		 return toDTO(this.repository.findById(id)
	                .orElseThrow(() -> new GenericException(MensagemPersonalizada.
	                        ALERTA_ELEMENTO_NAO_ENCONTRADO,
	                    Conta.class
	                        .getSimpleName())));
	}

	@Override
	public List<EnderecoDTO> encontrarTodos() {
		 return this.repository.findAll().stream().map(EnderecoService::toDTO).collect(Collectors.toList());
	}
}

