package org.example.Services.Tarefa;

import org.example.Model.Estado;
import org.example.Model.Tarefa;
import org.example.Repository.TarefaRepository;
import org.example.Utils.TarefaExistenteException;
import org.example.Utils.TarefaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AtualizarTarefaService {
    @Autowired
    private TarefaRepository tarefaRepository;

    private Tarefa atualizar(Tarefa tarefa){
        Tarefa oldTarefa = tarefaRepository.findById(tarefa.getId())
                .orElseThrow(TarefaNotFoundException::new);

        if (!validEstado(oldTarefa,tarefa.getEstado()))
            throw new IllegalArgumentException("Inconsistência ao atualizar estado.");

        if (tarefaRepository.existsByDescricaoAndIdNot(tarefa.getDescricao(), tarefa.getId()))
            throw new TarefaExistenteException(tarefa.getDescricao());

        return tarefaRepository.save(tarefa);
    }

    private Tarefa setTarefaAtrasada(Tarefa tarefa){
        if (tarefa.getEstado().equals(Estado.CONCLUIDA))
            throw new IllegalArgumentException("Esta tarefa já foi concluída.");
        if (tarefa.getEstado().equals(Estado.ATRASADA))
            throw new IllegalArgumentException("Esta tarefa já está atrasada.");
        if (tarefa.getDataVencimento().isAfter(LocalDateTime.now()))
            throw new IllegalArgumentException("Esta tarefa ainda está dentro do prazo.");

        tarefa.setEstado(Estado.ATRASADA);
        return tarefaRepository.save(tarefa);
    }

    private boolean validEstado(Tarefa tarefa, Estado estado){
        if (tarefa.getEstado().equals(Estado.ATRASADA) &&
                (estado.equals(Estado.BACKLOG) || estado.equals(Estado.EM_ANDAMENTO)))
                return false;
        if (tarefa.getEstado().equals(Estado.CONCLUIDA) &&
                (!estado.equals(Estado.CONCLUIDA)))
                return false;

        return true;
    }
}
