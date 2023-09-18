package com.minsait.treinamento.model.entities;

import com.minsait.treinamento.model.embedded.Documentacao;
import com.minsait.treinamento.model.embedded.InfoFinanceiraUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "usuario")
public class Usuario extends GenericEntity<Long> {

    @Column(nullable = false, length = 300)
    private String nome;

    @Embedded
    @Default
    private InfoFinanceiraUsuario infoFinanceira = InfoFinanceiraUsuario.builder().rendaAnual(0.0).build();

    @Embedded
    private Documentacao documentacao;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Endereco> enderecos = new ArrayList<>();
}
