package org.example.Services.Tarefa;

import org.example.Model.Tarefa;
import org.example.Repository.TarefaRepository;
import org.example.Utils.TarefaExistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalvarTarefaService {

    @Autowired
    TarefaRepository tarefaRepository;

    public Tarefa executar(Tarefa tarefa){
       if (tarefaRepository.existsByDescricao(tarefa.getDescricao())){
           throw new TarefaExistenteException(tarefa.getDescricao());
       }
       return tarefaRepository.save(tarefa);
    }
}
