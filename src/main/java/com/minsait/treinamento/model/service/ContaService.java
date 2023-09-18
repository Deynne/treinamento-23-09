package com.minsait.treinamento.model.service;

import com.minsait.treinamento.dtos.IdentificadorBasicoDTO;
import com.minsait.treinamento.dtos.conta.*;
import com.minsait.treinamento.dtos.usuario.UsuarioDTO;
import com.minsait.treinamento.exceptions.GenericException;
import com.minsait.treinamento.exceptions.MensagemPersonalizada;
import com.minsait.treinamento.model.embedded.Documentacao;
import com.minsait.treinamento.model.entities.Conta;
import com.minsait.treinamento.model.entities.Usuario;
import com.minsait.treinamento.model.repositories.ContaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContaService extends GenericCrudServiceImpl<ContaRepository, Long, ContaInsertDTO, ContaUpdateDTO, ContaDTO> {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    @Transactional
    public ContaDTO salvar(@Valid ContaInsertDTO dto) {
        Usuario u = this.usuarioService.encontrarEntidadePorId(dto.getIdUsuario());

        Conta c = Conta.builder()
                .numAgencia(dto.getNumAgencia())
                .numConta(dto.getNumConta())
                .saldo(0.0)
                .usuario(u)
                .build();

        c = this.repository.save(c);

        return toDTO(c);
    }


    public ContaDTO toDTO(Conta c) {

        UsuarioDTO usuarioDTO = this.usuarioService.encontrarPorId(c.getUsuario().getId());

        return ContaDTO.builder()
                .idUsuario(c.getUsuario().getId())
                .id(c.getId())
                .numAgencia(c.getNumAgencia())
                .numConta(c.getNumConta())
                .usuario(IdentificadorBasicoDTO.
                        <Long>builder()
                        .id(usuarioDTO.getId())
                        .nome(usuarioDTO.getNome())
                        .build())
                .saldo(c.getSaldo())
                .build();
    }

    @Override
    @Transactional
    public ContaDTO atualizar(@Valid ContaUpdateDTO dto) {
        Conta c = this.repository.findById(dto.getId())
                .orElseThrow(() -> new GenericException(MensagemPersonalizada.
                        ALERTA_ELEMENTO_NAO_ENCONTRADO,
                        Conta.class
                                .getSimpleName()));
        if (dto.getNumAgencia() != null) {
            c.setNumAgencia(dto.getNumAgencia());
        }

        if (dto.getNumConta() != null) {
            c.setNumConta(dto.getNumConta());
        }

        if (dto.getSaldo() != null) {
            c.setSaldo(dto.getSaldo());
        }

        if (dto.getIdUsuario() != null) {
            Usuario u = this.usuarioService.encontrarEntidadePorId(dto.getIdUsuario());

            if (!u.getId().equals(c.getUsuario().getId())) {
                c.setUsuario(u);
            }
        }

        this.repository.save(c);
        return toDTO(c);
    }

    @Override
    public ContaDTO excluir(@NotNull @Positive Long id) {
        Conta c = this.repository.findById(id)
                .orElseThrow(() -> new GenericException(MensagemPersonalizada.
                        ALERTA_ELEMENTO_NAO_ENCONTRADO,
                        Conta.class
                                .getSimpleName()));
        this.repository.delete(c);

        return toDTO(c);
    }

    @Override
    public ContaDTO encontrarPorId(@NotNull @Positive Long id) {
        return toDTO(this.repository.findById(id)
                .orElseThrow(() -> new GenericException(MensagemPersonalizada.
                        ALERTA_ELEMENTO_NAO_ENCONTRADO,
                        Conta.class
                                .getSimpleName())));
    }

    @Override
    public List<ContaDTO> encontrarTodos() {
        return this.repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ContaDTO depositar(@Valid ContaDepositarSacarDTO contaDepositar) {
        Conta conta = this.repository.findByNumContaAndNumAgencia(contaDepositar.getNumConta(), contaDepositar.getNumAgencia());
        double saldo = conta.getSaldo() + contaDepositar.getSaldo();
        conta.setSaldo(saldo);
        this.repository.save(conta);
        return toDTO(conta);
    }

    public ContaDTO sacar(@Valid ContaDepositarSacarDTO contaSacar) {
        Conta conta = this.repository.findByNumContaAndNumAgencia(contaSacar.getNumConta(), contaSacar.getNumAgencia());
        double saldo = conta.getSaldo() - contaSacar.getSaldo();
        if (saldo < 0) {
            throw new GenericException(MensagemPersonalizada.ALERTA_SALDO_INSUFICIENTE, Conta.class.getSimpleName());
        }
        conta.setSaldo(saldo);
        this.repository.save(conta);
        return toDTO(conta);
    }

    @Transactional
    public ContaDTO transferencia(@Valid ContaTransferenciaDTO transferencia) {

        Conta contaOrigem = this.repository.findByNumContaAndNumAgencia(transferencia.getContaOrigem(), transferencia.getAgenciaOrigem());
        Conta contaDestino = this.repository.findByNumContaAndNumAgencia(transferencia.getContaDestino(), transferencia.getAgenciaDestino());
        String cpfDestino = this.usuarioService.encontrarPorId(contaDestino.getUsuario().getId()).getCpf();

        if (!contaOrigem.getUsuario().getId().equals(contaDestino.getUsuario().getId())) {
            if (transferencia.getCpf() == null) {
                throw new GenericException(MensagemPersonalizada.ALERTA_CPF_INVALIDO, Documentacao.class.getSimpleName());
            }
        }
        if (transferencia.getCpf() != null) {
            if (!transferencia.getCpf().equals(cpfDestino)) {
                throw new GenericException(MensagemPersonalizada.ALERTA_CPF_INVALIDO, Documentacao.class.getSimpleName());
            }
        }
        if ((contaOrigem.getSaldo() - transferencia.getSaldo()) < 0) {
            throw new GenericException(MensagemPersonalizada.ALERTA_SALDO_INSUFICIENTE, Conta.class.getSimpleName());
        }
        if (contaOrigem.getNumAgencia().equals(contaDestino.getNumAgencia())
                && contaOrigem.getNumConta().equals(contaDestino.getNumConta())) {
            throw new GenericException(MensagemPersonalizada.ALERTA_ELEMENTO_NAO_ENCONTRADO, Conta.class.getSimpleName());
        }

        double saldoOrigem = contaOrigem.getSaldo() - transferencia.getSaldo();
        double saldoDestino = contaDestino.getSaldo() + transferencia.getSaldo();
        contaOrigem.setSaldo(saldoOrigem);
        contaDestino.setSaldo(saldoDestino);

        this.repository.save(contaOrigem);
        this.repository.save(contaDestino);

        return toDTO(contaOrigem);
    }


    public List<ContaDTO> acharTodasPorUsuario(@NotNull @Positive Long id) {
        Usuario u = this.usuarioService.encontrarEntidadePorId(id);

        return this.repository.findAllByUsuarioOrderBySaldoDesc(u)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ContaDTO> acharTodasPorUsuarioEValorMinimoOrdemParametro(@NotNull @Positive Long id, @PositiveOrZero @NotNull Double valorMinimo) {
        Usuario u = this.usuarioService.encontrarEntidadePorId(id);

        return this.repository.acharContasComDinheiroOrdemParametro(u, valorMinimo)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    public List<ContaDTO> acharTodasPorUsuarioEValorNomeParametro(@NotNull @Positive Long id, @PositiveOrZero @NotNull Double valorMinimo) {
        Usuario u = this.usuarioService.encontrarEntidadePorId(id);

        return this.repository.acharContasComDinheiroNomeParametro(u, valorMinimo)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ContaDTO> acharTodasPorUsuarioEValorMinimoQueryNativa(@NotNull @Positive Long id, @PositiveOrZero @NotNull Double valorMinimo) {
        Usuario u = this.usuarioService.encontrarEntidadePorId(id);

        return this.repository.acharContasComDinheiroQueryNativa(u, valorMinimo)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    public List<ContaDTO> achaContasPorNomeUsuario(@NotBlank @Size(min = 3, max = 300) String nome) {
        return this.repository.achaContasPorNomeUsuario(nome)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ContaDTO> achaContasPorNomeUsuarioQueryNativa(@NotBlank @Size(min = 3, max = 300) String nome) {
        return this.repository.achaContasPorNomeUsuarioQueryNativa(nome)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
