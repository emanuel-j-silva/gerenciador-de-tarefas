package org.example.Controller;

import org.example.Utils.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(TarefaExistenteException.class)
    public ResponseEntity<String> handleTarefaExistenteException(TarefaExistenteException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex){
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Valores de entrada inválidos.");
    }

    @ExceptionHandler(TarefaNotFoundException.class)
    public ResponseEntity<String> handleTarefaNotFoundException(TarefaNotFoundException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(TarefaEstadoCriacaoException.class)
    public ResponseEntity<String> handleTarefaEstadoCriacaoException(TarefaEstadoCriacaoException ex){
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ex.getMessage());
    }

}
