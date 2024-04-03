package org.example.Utils;

public class TarefaNotFoundException extends RuntimeException{
    public TarefaNotFoundException(){
        super("Tarefa não encontrada");
    }
}
