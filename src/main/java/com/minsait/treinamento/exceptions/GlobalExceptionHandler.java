package com.minsait.treinamento.exceptions;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
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
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.minsait.treinamento.dtos.exceptions.ExceptionDTO;
import com.minsait.treinamento.utils.ConstraintUtils;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        final ExceptionDTO body = ExceptionDTO.builder()
                                              .codigo(MensagemPersonalizada.ERRO_METODO_NAO_SUPORTADO.getCodigoMsg())
                                              .httpStatus(status.value())
                                              .tipo(MensagemPersonalizada.ERRO_METODO_NAO_SUPORTADO.getSeveridade())
                                              .build();

        body.addDetalhe(ex.getMessage());

        return this.handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        // TODO Auto-generated method stub
        return super.handleHttpMediaTypeNotSupported(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        // TODO Auto-generated method stub
        return super.handleHttpMediaTypeNotAcceptable(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        // TODO Auto-generated method stub
        return super.handleMissingPathVariable(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        // TODO Auto-generated method stub
        return super.handleMissingServletRequestParameter(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        // TODO Auto-generated method stub
        return super.handleServletRequestBindingException(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        // TODO Auto-generated method stub
        return super.handleConversionNotSupported(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        // TODO Auto-generated method stub
        return super.handleTypeMismatch(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        // TODO Auto-generated method stub
        return super.handleHttpMessageNotReadable(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        // TODO Auto-generated method stub
        return super.handleHttpMessageNotWritable(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        // TODO Auto-generated method stub
        return super.handleMethodArgumentNotValid(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        // TODO Auto-generated method stub
        return super.handleMissingServletRequestPart(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        // TODO Auto-generated method stub
        return super.handleBindException(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        // TODO Auto-generated method stub
        return super.handleNoHandlerFoundException(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex,
            HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
        // TODO Auto-generated method stub
        return super.handleAsyncRequestTimeoutException(ex, headers, status, webRequest);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        this.printException(ex, request);

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute("javax.servlet.error.exception", ex, RequestAttributes.SCOPE_REQUEST);
        }

        ExceptionDTO exceptionVO = null;

        if (body instanceof ExceptionDTO) {

            exceptionVO = (ExceptionDTO) body;
        } else if (ex instanceof MethodArgumentNotValidException) {
            exceptionVO = new ExceptionDTO();
            exceptionVO.setCodigo(MensagemPersonalizada.ERRO_VALIDACAO_CAMPO.getCodigoMsg());
            exceptionVO.addDetalhe(MensagemPersonalizada.ERRO_VALIDACAO_CAMPO.getDescricaoMsg());
            exceptionVO.setCodigo("erro");
            exceptionVO.setHttpStatus(status.value());
            MethodArgumentNotValidException mex = (MethodArgumentNotValidException) ex;

            for (ObjectError err : mex.getBindingResult().getAllErrors()) {
                FieldError ferr = (FieldError) err;
                exceptionVO.addDetalhe(ConstraintUtils.getConstraintMessage(ferr.getCode(),ferr.getField()));
            }

            log.info("");

        } else if (ex instanceof BindException) {
            exceptionVO = new ExceptionDTO();
            exceptionVO.setCodigo(MensagemPersonalizada.ERRO_VALIDACAO_CAMPO.getCodigoMsg());
            exceptionVO.addDetalhe(MensagemPersonalizada.ERRO_VALIDACAO_CAMPO.getDescricaoMsg());
            exceptionVO.setHttpStatus(status.value());
            exceptionVO.setTipo("erro");
            BindException bex = (BindException) ex;
            for (ObjectError err : bex.getAllErrors()) {
                FieldError ferr = (FieldError) err;
                exceptionVO.addDetalhe(ConstraintUtils.getConstraintMessage(ferr.getCode(), ferr.getField()));
            }
        } else {
            exceptionVO = new ExceptionDTO();
            exceptionVO.setCodigo(MensagemPersonalizada.ERRO_ACESSO_NEGADO.getCodigoMsg());
            exceptionVO.addDetalhe(MensagemPersonalizada.ERRO_ACESSO_NEGADO.getDescricaoMsg());
            exceptionVO.setHttpStatus(status.value());
            exceptionVO.setTipo("erro");
        }

        return new ResponseEntity<>(exceptionVO, headers, status);
    }
    
    private void printException(Exception ex, WebRequest request) {
        // TODO Auto-generated method stub
        
    }

    @ExceptionHandler(value = {Exception.class,GenericException.class})
    protected ResponseEntity<Object> trataExcessaoAplicacao(final GenericException e, final WebRequest r) {
        ExceptionDTO body = new ExceptionDTO(e.getValidacao(),HttpStatus.BAD_REQUEST.value(),null);
        return this.handleExceptionInternal(e, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, r);
    }

}
