package org.example.Controller;

import org.example.Utils.CategoriaExistenteException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CategoriaExistenteException.class)
    public ResponseEntity<String> handleCategoriaExistenteException(CategoriaExistenteException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
