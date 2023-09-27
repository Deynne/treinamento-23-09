package com.minsait.treinamento.controller.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minsait.treinamento.model.service.InfoService;

@RestController
@RequestMapping(path = "info", produces = MediaType.APPLICATION_JSON_VALUE)
public class InfoRest {
    
    @Autowired
    private InfoService service;

    @GetMapping("get-app-config")
    public ResponseEntity<List<String>> getApplicationPropertiesData() {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getInfo());
    }
    
    
   @RolesAllowed({"ROLE_ADMIN"})
   @Secured({"ROLE_ADMIN"})
   @PreAuthorize("hasRole('ROLE_VIEWER') or hasRole('ROLE_ADMIN')")
    @GetMapping("exception-test")
    public ResponseEntity<Object> getException() {
        return ResponseEntity.ok(this.service.throwException());
    }
}
