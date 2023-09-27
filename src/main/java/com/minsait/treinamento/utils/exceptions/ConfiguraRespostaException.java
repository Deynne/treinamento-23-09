package com.minsait.treinamento.utils.exceptions;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minsait.treinamento.dtos.exceptions.ExceptionDTO;
import com.minsait.treinamento.exceptions.MensagemPersonalizada;

public final class ConfiguraRespostaException {
    public static void setException(ObjectMapper mapper, HttpServletResponse response, MensagemPersonalizada mensagem, HttpStatus status) throws IOException, JsonProcessingException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(status.value());
        response.getWriter().write(mapper.writeValueAsString(new ExceptionDTO(mensagem,status.value())));
    }
}
