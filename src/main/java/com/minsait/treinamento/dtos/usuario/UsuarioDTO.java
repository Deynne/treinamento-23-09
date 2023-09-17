package com.minsait.treinamento.dtos.usuario;


import com.minsait.treinamento.dtos.endereco.EnderecoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {

    private Long id;

    private String nome;

    private double rendaAnual;

    private String cpf;

    private String rg;

    private List<EnderecoDTO> enderecos;
}
