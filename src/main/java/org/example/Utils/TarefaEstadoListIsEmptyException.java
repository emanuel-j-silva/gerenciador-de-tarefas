package org.example.Utils;

public class TarefaEstadoListIsEmptyException extends RuntimeException{
    public TarefaEstadoListIsEmptyException(String estado){
        super ("Não existem tarefas registradas no estado '" + estado + "'.");
    }
}
