package org.example.Utils;

public class TarefaListIsEmptyException extends RuntimeException{

    public TarefaListIsEmptyException(){
        super("Lista de tarefas vazia");
    }
}
