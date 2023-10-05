package com.minsait.treinamento.model.service;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.minsait.treinamento.dtos.Transacao.ExtratoUsuarioDTO;
import com.minsait.treinamento.dtos.usuario.UsuarioDTO;
import com.minsait.treinamento.dtos.usuario.UsuarioInsertDTO;
import com.minsait.treinamento.dtos.usuario.UsuarioUpdateDTO;
import com.minsait.treinamento.exceptions.GenericException;
import com.minsait.treinamento.exceptions.MensagemPersonalizada;
import com.minsait.treinamento.model.entities.Conta;
import com.minsait.treinamento.model.entities.Usuario;
import com.minsait.treinamento.model.repositories.UsuarioRepository;

@Service
public class UsuarioService extends GenericCrudServiceImpl<UsuarioRepository, Long, UsuarioInsertDTO, UsuarioUpdateDTO, UsuarioDTO> {

    @Autowired
    @Lazy
    private TransacaoService transacaoService;
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
        //Podemos realizar este processo assim, criando um novo objeto
                            //.infoFinanceira(InfoFinanceiraUsuario.builder().rendaAnual(dto.getRendaAnual()).build())
                            .build();
        
        // Ou assim, utilizando-se do objeto padrão
        u.getInfoFinanceira().setRendaAnual(dto.getRendaAnual());
        u.getDocumentacao().setCpf(dto.getCpf());
        u.getDocumentacao().setRg(dto.getRg());
        
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
        
        if(u.getBloqueado())
            throw new GenericException(MensagemPersonalizada.ERRO_USUARIO_BLOQUEADO,
                    Conta.class.getSimpleName());
        
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
        
        this.repository.save(u);
        
        return toDTO(u);
    }

    
    @Override
    @Transactional
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
        
        if(u.getBloqueado())
            throw new GenericException(MensagemPersonalizada.ERRO_USUARIO_BLOQUEADO,
                    Conta.class.getSimpleName());
        if(contaService.possuiConta(u))
            throw new GenericException(MensagemPersonalizada.ERRO_USUARIO_DELETE_CONTA_ATIVA,
                    Conta.class.getSimpleName());
        
        this.enderecoService.excluirPorUsuario(u);
        
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

    private static UsuarioDTO toDTO(@NotNull Usuario u) {
        return UsuarioDTO.builder()
                            .id(u.getId())
                            .nome(u.getNome())
                            .rendaAnual(u.getInfoFinanceira().getRendaAnual())
                            .cpf(u.getDocumentacao().getCpf())
                            .rg(u.getDocumentacao().getRg())
                            .bloqueado(u.getBloqueado())
                            .build();
    }

    
    public List<ExtratoUsuarioDTO> extrato(@NotNull @Positive Long id) {
        Usuario u = this.repository
                      .findById(id)
                      .orElseThrow(() -> new GenericException(
                                                  MensagemPersonalizada.ALERTA_ELEMENTO_NAO_ENCONTRADO,
                                                  Conta.class.getSimpleName()));
        if(u.getBloqueado())
            throw new GenericException(MensagemPersonalizada.ERRO_USUARIO_BLOQUEADO,
                    Conta.class.getSimpleName());
        return transacaoService.encontrarPorUsuario(u);
    }



    @Transactional
    public UsuarioDTO bloqueio(Long id, Boolean bloqueio, Boolean completo) {
        var u = this.repository.findById(id)
                .orElseThrow(() -> new GenericException(MensagemPersonalizada.ALERTA_ELEMENTO_NAO_ENCONTRADO,
                Conta.class.getSimpleName()));
        u.setBloqueado(bloqueio);
        
        if(completo) {
            contaService.bloquearPorUsuario(u, bloqueio);
        }        

        this.repository.save(u);
        
        return toDTO(u);
    }

}
