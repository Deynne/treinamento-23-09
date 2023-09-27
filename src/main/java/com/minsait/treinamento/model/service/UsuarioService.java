package com.minsait.treinamento.model.service;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.minsait.treinamento.dtos.IdentificadorBasicoDTO;
import com.minsait.treinamento.dtos.usuario.UsuarioDTO;
import com.minsait.treinamento.dtos.usuario.UsuarioInsertDTO;
import com.minsait.treinamento.dtos.usuario.UsuarioUpdateDTO;
import com.minsait.treinamento.exceptions.GenericException;
import com.minsait.treinamento.exceptions.MensagemPersonalizada;
import com.minsait.treinamento.model.entities.Usuario;
import com.minsait.treinamento.model.entities.embedded.Documentacao;
import com.minsait.treinamento.model.entities.enumerators.security.CargosSistema;
import com.minsait.treinamento.model.repositories.UsuarioRepository;
import com.minsait.treinamento.model.security.UsuarioAutenticador;

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
    
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;
    
    @Override
    public UsuarioDTO salvar(UsuarioInsertDTO dto) {
        Usuario u = Usuario.builder()
                            .nome(dto.getNome())
                            .documentacao(Documentacao.builder().cpf(dto.getCpf()).rg(dto.getRg()).build())
                            .bloqueado(false)
                            .usuario(dto.getUsuario())
                            .senha(passwordEncoder.encode(dto.getSenha()))
                            .cargos(dto.getCargos())
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
        UsuarioAutenticador<Long> usuarioAutenticado = this.autenticado();
        if(dto.getUsuario() != null) {
            if(usuarioAutenticado != null && usuarioAutenticado.getAuthorities().contains(CargosSistema.ADMINISTRADOR)) {
                u.setUsuario(dto.getUsuario());
            }
        }
        
        if(dto.getSenha() != null) {
            if(usuarioAutenticado != null && usuarioAutenticado.getAuthorities().contains(CargosSistema.ADMINISTRADOR)) {
                u.setSenha(passwordEncoder.encode(dto.getSenha()));
            }
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
    
    @SuppressWarnings("unchecked")
    public UsuarioAutenticador<Long> autenticado() {
        try {
            SecurityContext context = SecurityContextHolder.getContext();
            if(context != null) {
                Authentication auth = context.getAuthentication();
                if(auth != null) {
                    Object u = auth.getPrincipal();
                    if(u instanceof UsuarioAutenticador) {
                        return (UsuarioAutenticador<Long>) u;
                    }
                }
            }
            return (UsuarioAutenticador<Long>) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new GenericException(MensagemPersonalizada.ERRO_ACESSO_NEGADO,e);
        }
    }



    public Usuario encontrarEntidadePorUsuario(String usuario) {
        return this.repository.findByUsuario(usuario)
                .orElseThrow(() -> {
                    return new GenericException(MensagemPersonalizada.
                                                    ALERTA_ELEMENTO_NAO_ENCONTRADO,
                                                Usuario.class
                                                    .getSimpleName());
            });
    }

    public Usuario atualizarSenha(String usuario, String senhaNova) {
        Usuario u = this.encontrarEntidadePorUsuario(usuario);
        u.setSenha(senhaNova);
        
        return this.repository.save(u);
    }



    public UsuarioDTO excluir(String usuario) {
        Usuario u = this.encontrarEntidadePorUsuario(usuario);
        this.repository.delete(u);
        return toDTO(u);
    }
    
}
