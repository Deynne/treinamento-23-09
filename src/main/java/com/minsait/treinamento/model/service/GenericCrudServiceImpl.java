package com.minsait.treinamento.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import com.minsait.treinamento.model.interfaces.GenericCrudService;
import com.minsait.treinamento.model.repositories.GenericCrudRepository;

@Validated
public abstract class GenericCrudServiceImpl<R extends GenericCrudRepository<?,I>,
                                    I extends Number,
                                    P,U,B> implements GenericCrudService<I,P,U,B> {

    @Autowired
    public R repository;
    
}
