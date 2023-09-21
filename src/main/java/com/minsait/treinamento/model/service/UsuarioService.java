package com.minsait.treinamento.model.service;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.minsait.treinamento.dtos.IdentificadorBasicoDTO;
import com.minsait.treinamento.dtos.conta.ContaUpdateDTO;
import com.minsait.treinamento.dtos.usuario.UsuarioDTO;
import com.minsait.treinamento.dtos.usuario.UsuarioInsertDTO;
import com.minsait.treinamento.dtos.usuario.UsuarioUpdateDTO;
import com.minsait.treinamento.exceptions.GenericException;
import com.minsait.treinamento.exceptions.MensagemPersonalizada;
import com.minsait.treinamento.model.entities.Usuario;
import com.minsait.treinamento.model.entities.embedded.Documentacao;
import com.minsait.treinamento.model.repositories.UsuarioRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UsuarioService extends GenericCrudServiceImpl<UsuarioRepository, Long, UsuarioInsertDTO, UsuarioUpdateDTO, UsuarioDTO> {


    @Autowired
    @Lazy
    private EnderecoService enderecoService;
    
    @Autowired
    @Lazy
    private ContaService contaService;
    
    @Override
    public UsuarioDTO salvar(UsuarioInsertDTO dto) {
        Usuario u = Usuario.builder()
                            .nome(dto.getNome())
                            .documentacao(Documentacao.builder().cpf(dto.getCpf()).rg(dto.getRg()).build())
                            .bloqueado(false)
                            //Podemos realizar este processo assim, criando um novo objeto
//                            .infoFinanceira(InfoFinanceiraUsuario.builder().rendaAnual(dto.getRendaAnual()).build())
                            .build();
        // Ou assim, utilizando-se do objeto padrão
        u.getInfoFinanceira().setRendaAnual(dto.getRendaAnual());
        
        u = this.repository.save(u);
        
        return toDTO(u);
    }
    


    @Override
    public UsuarioDTO atualizar(UsuarioUpdateDTO dto) {
        Usuario u = this.repository.findById(dto.getId())
                                    .orElseThrow(() -> 
                                        new GenericException(MensagemPersonalizada.
                                                                ALERTA_ELEMENTO_NAO_ENCONTRADO,
                                                            Usuario.class
                                                                .getSimpleName()));
        
        if(dto.getNome() != null) {
            u.setNome(dto.getNome());
        }
        
        if(dto.getRendaAnual() != null) {
            u.getInfoFinanceira().setRendaAnual(dto.getRendaAnual());
        }
        
        if(dto.getCpf() != null) {
            u.getDocumentacao().setCpf(dto.getCpf());
        }
        
        if(dto.getRg() != null) {
            u.getDocumentacao().setRg(dto.getRg());
        }
        
        if(dto.getBloqueado() != null) {
            u.setBloqueado(dto.getBloqueado());
        }
        
        this.repository.save(u);
        
        return toDTO(u);
    }

    
    @Override
    public UsuarioDTO excluir(@NotNull @Positive Long id) {
        Usuario u = this.repository.findById(id)
                /**
                 * Quando passamos uma função anonima para o orElseThrow
                 * isto equivale a criar esta função Supplier
                 */
                .orElseThrow(new Supplier<GenericException>() {
                                @Override
                                public GenericException get() {
                                    return new GenericException(MensagemPersonalizada.
                                                                    ALERTA_ELEMENTO_NAO_ENCONTRADO,
                                                                Usuario.class
                                                                    .getSimpleName());
                                }
                            });
        
        
        this.enderecoService.excluirPorIdUsuario(id);   
        this.contaService.excluirPorIdUsuario(id);
        this.repository.delete(u);
        
        return toDTO(u);
    }

    @Override
    public UsuarioDTO encontrarPorId(@NotNull @Positive Long id) {
        return toDTO(encontrarEntidadePorId(id));
    }



    public Usuario encontrarEntidadePorId(@NotNull @Positive Long id) {
        return this.repository.findById(id)
                    .orElseThrow(() -> {
                            return new GenericException(MensagemPersonalizada.
                                                            ALERTA_ELEMENTO_NAO_ENCONTRADO,
                                                        Usuario.class
                                                            .getSimpleName());
                    });
    }

    @Override
    public List<UsuarioDTO> encontrarTodos() {
        return this.repository.findAll()
                                .stream()
                                .map(UsuarioService::toDTO)
                                .collect(Collectors.toList());
    }

    public static UsuarioDTO toDTO(@NotNull Usuario u) {
        return UsuarioDTO.builder()
                            .id(u.getId())
                            .nome(u.getNome())
                            .rendaAnual(u.getInfoFinanceira().getRendaAnual())
                            .cpf(u.getDocumentacao().getCpf())
                            .rg(u.getDocumentacao().getRg())
                            .bloqueado(u.isBloqueado())
                            .build();
    }
    
    public boolean alterarEstadoBloqueioCascata(@NotNull @Positive Long id, boolean bloqueado) {
        boolean resultado = this.atualizar(UsuarioUpdateDTO.builder().id(id).bloqueado(bloqueado).build()).isBloqueado();
        
        if(bloqueado) {
            this.contaService.bloquearPorUsuario(id);
        }
        else {
            this.contaService.desbloquearPorUsuario(id);
        }
        
        return resultado;
        
    }
    
    public boolean alterarEstadoBloqueio(@NotNull @Positive Long id, boolean bloqueado) {
        return this.atualizar(UsuarioUpdateDTO.builder().id(id).bloqueado(bloqueado).build()).isBloqueado();
    }

    public void find(Long id) {
        IdentificadorBasicoDTO<Long> i =  this.repository.findUsuario(id);
        
        log.info(i.toString());
    }
    
}
