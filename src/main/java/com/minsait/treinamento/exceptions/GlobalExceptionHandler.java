package com.minsait.treinamento.exceptions;

import javax.servlet.ServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.minsait.treinamento.dtos.exceptions.ExceptionDTO;
import com.minsait.treinamento.model.entities.Endereco;
import com.minsait.treinamento.utils.ConstraintUtils;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        MensagemPersonalizada mensagem = MensagemPersonalizada.ERRO_METODO_NAO_SUPORTADO;
        final ExceptionDTO body = new ExceptionDTO(mensagem, status.value(), ex.getMethod());

        return this.handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        MensagemPersonalizada mensagem = MensagemPersonalizada.ERRO_TIPO_MIDIA_NAO_SUPORTADO;
        final ExceptionDTO body = new ExceptionDTO(mensagem, status.value(), ex.getContentType().toString());

        return this.handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        MensagemPersonalizada mensagem = MensagemPersonalizada.ERRO_TIPO_MIDIA_NAO_ACEITO;
        final ExceptionDTO body = new ExceptionDTO(mensagem, status.value());

        return this.handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        MensagemPersonalizada mensagem = MensagemPersonalizada.ERRO_PARAMETRO_CAMINHO_AUSENTE;
        final ExceptionDTO body = new ExceptionDTO(mensagem, status.value(), ex.getVariableName());

        return this.handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        MensagemPersonalizada mensagem = MensagemPersonalizada.ERRO_PARAMETRO_AUSENTE;
        final ExceptionDTO body = new ExceptionDTO(mensagem, status.value(), ex.getParameterName());

        return this.handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        MensagemPersonalizada mensagem = MensagemPersonalizada.ERRO_INESPERADO;
        final ExceptionDTO body = new ExceptionDTO(mensagem, status.value());

        return this.handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        MensagemPersonalizada mensagem = MensagemPersonalizada.ERRO_INESPERADO;
        final ExceptionDTO body = new ExceptionDTO(mensagem, status.value());

        return this.handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        MensagemPersonalizada mensagem = MensagemPersonalizada.ERRO_INESPERADO;
        final ExceptionDTO body = new ExceptionDTO(mensagem, status.value());

        return this.handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        MensagemPersonalizada mensagem = MensagemPersonalizada.ERRO_INESPERADO;
        final ExceptionDTO body = new ExceptionDTO(mensagem, status.value());

        return this.handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        MensagemPersonalizada mensagem = MensagemPersonalizada.ERRO_INESPERADO;
        final ExceptionDTO body = new ExceptionDTO(mensagem, status.value());

        return this.handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionDTO body = new ExceptionDTO(MensagemPersonalizada.ERRO_VALIDACAO_CAMPO,HttpStatus.BAD_REQUEST.value(),null);
        
        ex.getAllErrors().forEach(error -> body.addDetalhe(ConstraintUtils.getConstraintMessage(error)));
        return this.handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        
        return super.handleMissingServletRequestPart(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        
        return super.handleBindException(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        
        return super.handleNoHandlerFoundException(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex,
            HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
        
        return super.handleAsyncRequestTimeoutException(ex, headers, status, webRequest);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute("javax.servlet.error.exception", ex, RequestAttributes.SCOPE_REQUEST);
        }

        ExceptionDTO exceptionVO = null;

        if (body instanceof ExceptionDTO) {

            exceptionVO = (ExceptionDTO) body;
        } else {
            exceptionVO = new ExceptionDTO(MensagemPersonalizada.ERRO_INESPERADO,status.value());
        }

        return new ResponseEntity<>(exceptionVO, headers, status);
    }
    
    @ExceptionHandler(value = {GenericException.class})
    protected ResponseEntity<Object> trataExcessaoAplicacao(final GenericException e, final WebRequest r) {
        ExceptionDTO body = new ExceptionDTO(e,HttpStatus.BAD_REQUEST.value());
        return this.handleExceptionInternal(e, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, r);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<Object> trataContentViolationException(final ConstraintViolationException e, WebRequest r) {
        ExceptionDTO body = new ExceptionDTO(MensagemPersonalizada.ERRO_VALIDACAO_CAMPO,HttpStatus.BAD_REQUEST.value(),null);
        e.getConstraintViolations().forEach(v -> {
            
            body.addDetalhe(ConstraintUtils.getConstraintMessage(v.getConstraintDescriptor().
                                                                    getAnnotation()
                                                                        .annotationType()
                                                                            .getSimpleName(), 
                                                                 v.getPropertyPath()
                                                                     .toString(), 
                                                                     v.getConstraintDescriptor().getAttributes()));
        });
        return this.handleExceptionInternal(e, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, r);
    }
    
    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    protected ResponseEntity<Object> trataDataIntegrityViolationException(final DataIntegrityViolationException e, WebRequest r) {
        ExceptionDTO body = new ExceptionDTO(MensagemPersonalizada.ERRO_INTEGRIDADE_DO_BANCO_VIOLADA
                                            ,HttpStatus.BAD_REQUEST.value()
                                            ,Endereco.class.getSimpleName());
        return this.handleExceptionInternal(e, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, r);
    }
}
