package org.example.Utils;

public class CategoriaNotFoundException extends RuntimeException{
    public CategoriaNotFoundException(){
        super("Categoria não econtrada.");
    }
}
