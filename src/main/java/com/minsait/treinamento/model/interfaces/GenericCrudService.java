package com.minsait.treinamento.model.interfaces;

import java.util.List;

public interface GenericCrudService<I extends Number, P, U, B> {
    public B salvar(P dto);
    
    public B atualizar(U dto);
    
    public B excluir(I id);
    
    public B encontrarPorId(I id);
    
    public List<B> encontrarTodos();
}
