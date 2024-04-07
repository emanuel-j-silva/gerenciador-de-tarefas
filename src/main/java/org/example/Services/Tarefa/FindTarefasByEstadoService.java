package org.example.Services.Tarefa;

import org.example.Model.Estado;
import org.example.Model.Tarefa;
import org.example.Repository.TarefaRepository;
import org.example.Utils.TarefaEstadoListIsEmptyException;
import org.example.Utils.TarefaListIsEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindTarefasByEstadoService {

    @Autowired
    TarefaRepository tarefaRepository;
    public List<Tarefa> executar(Estado estado){
        if (tarefaRepository.findAll().isEmpty()) {
            throw new TarefaListIsEmptyException();
        }
        List<Tarefa> tarefas = tarefaRepository.findAll().stream()
                .filter(tarefa -> tarefa.getEstado() != null && tarefa.getEstado() == estado)
                .collect(Collectors.toList());
        if (tarefas.isEmpty()){
            throw new TarefaEstadoListIsEmptyException(estado.name());
        }
        return tarefas;
    }
}
