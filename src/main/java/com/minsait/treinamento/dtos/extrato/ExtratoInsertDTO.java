package com.minsait.treinamento.dtos.extrato;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExtratoInsertDTO {
	
	@NotNull
    @Positive
	private Long idContaVinculada;
    
	@NotNull
	private LocalDateTime dataInicio;
	
	
}