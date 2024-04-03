package org.example.Services.Tarefa;

import org.example.Model.Tarefa;
import org.example.Repository.TarefaRepository;
import org.example.Utils.TarefaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindTarefaByIdService {

    @Autowired
    TarefaRepository tarefaRepository;

    public Tarefa executar(Long id){
        Tarefa tarefaExistente = tarefaRepository.findById(id)
                .orElseThrow(()->new TarefaNotFoundException());
        return tarefaExistente;
    }
}
