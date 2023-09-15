package com.minsait.treinamento.controller.rest;

import com.minsait.treinamento.dtos.conta.ContaDTO;
import com.minsait.treinamento.dtos.conta.ContaInsertDTO;
import com.minsait.treinamento.dtos.conta.ContaUpdateDTO;
import com.minsait.treinamento.model.service.ContaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.*;
import java.util.List;

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

}
