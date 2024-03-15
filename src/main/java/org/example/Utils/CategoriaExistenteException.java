package org.example.Utils;

public class CategoriaExistenteException extends RuntimeException{
    public CategoriaExistenteException(String nome){
        super("Uma categoria com o nome '" + nome + "' já existe.");
    }
}
