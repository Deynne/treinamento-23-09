package com.minsait.treinamento.model.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@ToString
@EqualsAndHashCode
@MappedSuperclass
public class GenericEntity<T extends Number> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected T id;
}
