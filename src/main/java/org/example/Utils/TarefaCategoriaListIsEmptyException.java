package org.example.Utils;

public class TarefaCategoriaListIsEmptyException extends RuntimeException{

    public TarefaCategoriaListIsEmptyException(){
        super("Não existem tarefas registradas nessa categoria.");
    }
}
