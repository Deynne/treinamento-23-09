package com.minsait.treinamento.dtos.security.token;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenJwtDTO {
    private Long id;
    private Long idUsuario;
    private Instant dataCriacao;
    private Instant dataTermino;
    private String token;
}
