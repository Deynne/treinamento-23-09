package com.minsait.treinamento.dtos.security.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JWTDataDTO {
    private String secret;
    private long expiration;
}
