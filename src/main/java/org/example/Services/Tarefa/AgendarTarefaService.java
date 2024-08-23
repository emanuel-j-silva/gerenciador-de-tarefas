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
    private AtualizacaoAutomaticaTarefaService atualizacaoAutomaticaTarefa;

    public void agendarVerificacaoTarefa(Tarefa tarefa){
        LocalDateTime dataVencimento = tarefa.getDataVencimento();
        if (dataVencimento != null){
            long delay = Duration.between(LocalDateTime.now(),tarefa.getDataVencimento()).toMillis();
            scheduledExecutorService.schedule(
                    ()-> atualizacaoAutomaticaTarefa.verificarEAtualizarTarefa(tarefa), delay,TimeUnit.MILLISECONDS
            );
        }

    }
}
