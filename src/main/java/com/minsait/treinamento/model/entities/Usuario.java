package com.minsait.treinamento.model.entities;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import com.minsait.treinamento.model.embedded.InfoFinanceiraUsuario;
import com.minsait.treinamento.model.entities.embedded.Documentacao;
import com.minsait.treinamento.model.entities.enumerators.security.CargosSistema;

import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name= "usuario")
public class Usuario extends GenericEntity<Long>{

    @Setter()
    @Column(nullable = false,length = 300)
    private String nome;
    
    @Embedded
    @Default
    private InfoFinanceiraUsuario infoFinanceira = InfoFinanceiraUsuario.builder().rendaAnual(0.0).build();
    
    @Embedded
    private Documentacao documentacao;
    
    @Column(length = 100, nullable = false, unique = true)
    private String usuario;
    
    @Column(length = 500, nullable = false)
    private String senha;
    
    @ElementCollection(fetch = FetchType.EAGER, targetClass = CargosSistema.class)
    @CollectionTable(name = "cargos_usuario", joinColumns = {@JoinColumn(name = "id_usuario",nullable = false)})
    @Column(name = "cargo", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<CargosSistema> cargos;
    
    private boolean bloqueado;
}
