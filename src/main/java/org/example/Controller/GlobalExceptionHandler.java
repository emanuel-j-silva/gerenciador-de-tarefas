package org.example.Controller;

import org.example.Utils.CategoriaExistenteException;
import org.example.Utils.CategoriaListIsEmptyException;
import org.example.Utils.CategoriaNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CategoriaExistenteException.class)
    public ResponseEntity<String> handleCategoriaExistenteException(CategoriaExistenteException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(CategoriaNotFoundException.class)
    public ResponseEntity<String> handleCategoriaNotFoundException(CategoriaNotFoundException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(CategoriaListIsEmptyException.class)
    public ResponseEntity<String> handleCategoriaListIsEmptyException(CategoriaListIsEmptyException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
