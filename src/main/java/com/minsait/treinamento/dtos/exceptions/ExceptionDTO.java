package com.minsait.treinamento.dtos.exceptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.minsait.treinamento.exceptions.GenericException;
import com.minsait.treinamento.exceptions.MensagemPersonalizada;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class ExceptionDTO implements Serializable{
    private String codigo;
    @Default
    private String tipo = "erro";
    @Default
    private List<String> detalhes = new ArrayList<>();
    private Integer httpStatus;
    
    public ExceptionDTO(final String codigo, final String detalhe) {
        super();
        this.codigo = codigo;
        this.detalhes = new ArrayList<>();
        
        final String[] partes = detalhe.split(System.getProperty("line.separator"));
        if(partes.length > 0) {
            for (final String s : partes) {
                this.detalhes.add(s);
            }
        
        } else {
            this.detalhes.add(detalhe);
        }
    }
    
    public ExceptionDTO(MensagemPersonalizada mensagem, int httpStatus, Collection<String> detalhes) {
        this.codigo = mensagem.getCodigoMsg();
        this.detalhes = new ArrayList<>();
        String descricaoMsg = mensagem.getDescricaoMsg();
        if(descricaoMsg != null) {
            this.detalhes.add(descricaoMsg);
        }
        this.tipo = mensagem.getSeveridade();
        this.httpStatus = httpStatus;
    }
    
    public ExceptionDTO(GenericException e, int httpStatus) {
        this.codigo = e.getValidacao().getCodigoMsg();
        this.detalhes = new ArrayList<>();
        String descricaoMsg = e.getDescricaoMensagem();
        if(descricaoMsg != null) {
            this.detalhes.add(descricaoMsg);
        }
        this.tipo = e.getValidacao().getSeveridade();
        this.httpStatus = httpStatus;
    }
    
    public void addDetalhe(final String detalhe) {
        if(detalhe == null) {
            return;
        }
        this.detalhes.add(detalhe);
    }

    public void addDetalhes(final Collection<String> detalhes) {
        if(detalhes == null) {
            return;
        }
        this.detalhes.addAll(detalhes);
    }
    
    public List<String> getDetalhes() {
        return Collections.unmodifiableList(this.detalhes);
    }
}
