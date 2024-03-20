package org.example.Utils;

public class TarefaExistenteException extends RuntimeException{
    public TarefaExistenteException (String descricao){
        super("Uma tarefa com a descrição '" + descricao + "' já existe.");
    }

}
