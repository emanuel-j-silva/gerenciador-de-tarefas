package org.example.Utils;

public class TarefaEstadoCriacaoException extends RuntimeException {
    public TarefaEstadoCriacaoException(){
        super("Não é possível criar uma tarefa como 'ATRASADA'");
    }
}
