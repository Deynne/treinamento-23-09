package com.minsait.treinamento.controller.rest;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import com.minsait.treinamento.controller.interfaces.GenericCrudRest;
import com.minsait.treinamento.model.service.GenericCrudServiceImpl;

@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public abstract class GenericCrudRestImpl<S extends GenericCrudServiceImpl<?,I,P,U,B>,
                                          I extends Number,
                                          P,U,B> implements GenericCrudRest<I, P, U, B> {
    
    @Autowired
    public S service;

    @Override
    public ResponseEntity<B> salvar(@Valid P dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.salvar(dto));
    }

    @Override
    public ResponseEntity<B> atualizar(@Valid U dto) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.atualizar(dto));
    }

    @Override
    public ResponseEntity<B> excluir(
            @Positive @NotNull I id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.excluir(id));
    }

    @Override
    public ResponseEntity<B> encontrarPorId(
            @Positive @NotNull I id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.encontrarPorId(id));
    }

    @Override
    public ResponseEntity<List<B>> encontrarTodos() {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.encontrarTodos());
    }
}