package com.minsait.treinamento.controller.rest;

import com.minsait.treinamento.dtos.conta.*;
import com.minsait.treinamento.model.service.ContaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("conta")
public class ContaRest extends GenericCrudRestImpl<ContaService, Long, ContaInsertDTO, ContaUpdateDTO, ContaDTO> {

    @GetMapping("contas-por-usuario/{id}")
    public ResponseEntity<List<ContaDTO>> acharTodasPorUsuario(@NotNull @Positive @PathVariable Long id) {
        return ResponseEntity.ok(this.service.acharTodasPorUsuario(id));

    }

    @GetMapping("contas-com-dinheiro-ordem-parametro")
    public ResponseEntity<List<ContaDTO>> acharTodasPorUsuarioOrdemParametro(@NotNull @Positive @RequestParam Long id,
                                                                             @RequestParam @NotNull @PositiveOrZero Double valorMinimo) {
        return ResponseEntity.ok(this.service.acharTodasPorUsuarioEValorMinimoOrdemParametro(id, valorMinimo));

    }

    @GetMapping("contas-com-dinheiro-nome-parametro")
    public ResponseEntity<List<ContaDTO>> acharTodasPorUsuarioNomeParametro(@NotNull @Positive @RequestParam Long id,
                                                                            @RequestParam @NotNull @PositiveOrZero Double valorMinimo) {
        return ResponseEntity.ok(this.service.acharTodasPorUsuarioEValorNomeParametro(id, valorMinimo));

    }

    @GetMapping("contas-com-dinheiro-query-nativa")
    public ResponseEntity<List<ContaDTO>> acharTodasPorUsuarioQueryNativa(@NotNull @Positive @RequestParam Long id,
                                                                          @RequestParam @NotNull @PositiveOrZero Double valorMinimo) {
        return ResponseEntity.ok(this.service.acharTodasPorUsuarioEValorMinimoQueryNativa(id, valorMinimo));
    }

    @GetMapping("contas-por-nome")
    public ResponseEntity<List<ContaDTO>> achaContasPorNomeUsuario(@RequestParam @NotBlank @Size(min = 3, max = 300) String nome) {
        return ResponseEntity.ok(this.service.achaContasPorNomeUsuario(nome));
    }

    @GetMapping("contas-por-nome-query-nativa")
    public ResponseEntity<List<ContaDTO>> achaContasPorNomeUsuarioQueryNativa(@RequestParam @NotBlank @Size(min = 3, max = 300) String nome) {
        return ResponseEntity.ok(this.service.achaContasPorNomeUsuarioQueryNativa(nome));
    }

    @Override
    public ResponseEntity<ContaDTO> atualizar(@Valid ContaUpdateDTO dto) {
        return ResponseEntity.ok(this.service.atualizar(dto));
    }

    @PutMapping("depositar")
    public ResponseEntity<Double> depositar(@Valid @RequestBody TransacaoSimplesDTO dto) {
        return ResponseEntity.ok(this.service.deposito(dto));
    }

    @PutMapping("sacar")
    public ResponseEntity<Double> sacar(@Valid @RequestBody TransacaoSimplesDTO dto) {
        return ResponseEntity.ok(this.service.saque(dto));
    }

    @PutMapping("transferir")
    public ResponseEntity<Map<String, Double>> transferir(@Valid @RequestBody TransferenciaDTO dto) {
        return ResponseEntity.ok(this.service.transferencia(dto));
    }

    @PutMapping("bloquear")
    public ResponseEntity<Boolean> bloquear(@RequestParam @Positive @NotNull Long id) {
        return ResponseEntity.<Boolean>ok(this.service.bloquear(id));
    }

    @PutMapping("desbloquear")
    public ResponseEntity<Boolean> desbloquear(@RequestParam @Positive @NotNull Long id) {
        return ResponseEntity.<Boolean>ok(this.service.desbloquear(id));
    }

    @GetMapping("conta-jdbc-query")
    public ResponseEntity<ContaDTO> getContaPorJDBC(Long idUsuario, String numAgencia, String numConta) {
        return new ResponseEntity(this.service.getContaPorJDBCEntityManager(idUsuario, numAgencia, numConta), HttpStatus.OK);
    }

    @GetMapping("conta-jdbc-query2")
    public ResponseEntity<ContaDTO> getContaPorJDBCTemplate(Long idUsuario, String numAgencia, String numConta) {
        return new ResponseEntity(this.service.getContaPorJDBCTemplace(idUsuario, numAgencia, numConta), HttpStatus.OK);
    }
}
