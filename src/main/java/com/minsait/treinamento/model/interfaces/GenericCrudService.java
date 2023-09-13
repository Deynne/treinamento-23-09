package com.minsait.treinamento.model.interfaces;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.validation.annotation.Validated;

@Validated
public interface GenericCrudService<I extends Number, P, U, B> {
    public B salvar(@Valid P dto);
    
    public B atualizar(@Valid U dto);
    
    public B excluir(@NotNull @Positive I id);
    
    public B encontrarPorId(@NotNull @Positive I id);
    
    public List<B> encontrarTodos();
}
