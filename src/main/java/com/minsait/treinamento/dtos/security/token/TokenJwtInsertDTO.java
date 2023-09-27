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
public class TokenJwtInsertDTO {
    private Long idUsuario;
    private String token;
    private Instant dataCriacao;
    private Instant dataTermino;

}
