package org.example.Services.Tarefa;

import org.example.Model.Tarefa;
import org.example.Repository.TarefaRepository;
import org.example.Utils.TarefaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExcluirTarefaService {

    @Autowired
    TarefaRepository tarefaRepository;

    public String executar(Tarefa tarefa){
        if(!tarefaRepository.existsById(tarefa.getId())){
            throw new TarefaNotFoundException();
        }
        tarefaRepository.delete(tarefa);
        return ("Tarefa deletada com sucesso!");
    }
}
