package org.example.Services.Tarefa;

import org.example.Model.Estado;
import org.example.Model.Tarefa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class AgendarTarefaService {

    @Autowired
    private ScheduledExecutorService scheduledExecutorService;

    @Autowired
    private AtualizarTarefaService atualizarTarefaService;

    public void agendarAtualizacaoTarefa(Tarefa tarefa){
        long delay = Duration.between(LocalDateTime.now(),tarefa.getDataVencimento()).toMillis();
        scheduledExecutorService.schedule(() -> {
            if (!tarefa.getEstado().equals(Estado.ATRASADA) && !tarefa.getEstado().equals(Estado.CONCLUIDA)) {
                atualizarTarefaService.setTarefaAtrasada(tarefa);
                }
            }, delay, TimeUnit.MILLISECONDS
        );
    }
}
