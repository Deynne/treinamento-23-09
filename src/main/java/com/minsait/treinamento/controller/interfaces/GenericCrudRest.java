package com.minsait.treinamento.controller.interfaces;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Validated
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public interface GenericCrudRest<I extends Number, P,U,B> {
    @PostMapping
    public ResponseEntity<B> salvar(@RequestBody 
                                    @Valid 
                                    P dto);
    
    @PutMapping
    public ResponseEntity<B> atualizar(@RequestBody 
                                       @Valid 
                                       U dto);
    
    @DeleteMapping
    public ResponseEntity<B> excluir(@RequestParam 
                                     @Positive
                                     @NotNull
                                     I id);
    
    @GetMapping
    public ResponseEntity<B> encontrarPorId(@RequestParam 
                                      @Positive
                                      @NotNull
                                      I id);
    
    @GetMapping("/all")
    public ResponseEntity<List<B>> encontrarTodos();
}
