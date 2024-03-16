package org.example.Utils;

public class CategoriaListIsEmptyException extends RuntimeException{
    public CategoriaListIsEmptyException(){
        super("Lista de categorias vazia");
    }
}
