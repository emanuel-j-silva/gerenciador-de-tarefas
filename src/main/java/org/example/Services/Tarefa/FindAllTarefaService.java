package org.example.Services.Tarefa;

import org.example.Model.Tarefa;
import org.example.Repository.TarefaRepository;
import org.example.Utils.TarefaListIsEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllTarefaService {

    @Autowired
    TarefaRepository tarefaRepository;

    public List<Tarefa> executar(){
        List<Tarefa> tarefas = tarefaRepository.findAll();
        if (tarefas.isEmpty()){
            throw new TarefaListIsEmptyException();
        }
        return tarefas;
    }
}
