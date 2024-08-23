package org.example.Services.Tarefa;

import org.example.Model.Estado;
import org.example.Model.Tarefa;
import org.example.Repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AtualizacaoAutomaticaTarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    public void verificarEAtualizarTarefa(Tarefa tarefa) {
        if (tarefa.getEstado() != Estado.ATRASADA && tarefa.getEstado() != Estado.CONCLUIDA) {
            if (tarefa.getDataVencimento().isBefore(LocalDateTime.now())) {
                tarefa.setEstado(Estado.ATRASADA);
                tarefaRepository.save(tarefa);
            }
        }
    }
}
