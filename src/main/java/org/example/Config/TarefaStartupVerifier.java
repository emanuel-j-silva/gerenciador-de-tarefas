package org.example.Config;

import org.example.Model.Estado;
import org.example.Model.Tarefa;
import org.example.Repository.TarefaRepository;
import org.example.Services.Tarefa.AtualizacaoAutomaticaTarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class TarefaStartupVerifier {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private AtualizacaoAutomaticaTarefaService atualizacaoAutomaticaTarefa;

    @EventListener(ApplicationReadyEvent.class)
    public void verificarTarefasAtrasadasAoIniciar(){
        List<Tarefa> tarefas = tarefaRepository.findByEstadoIn(Arrays.asList(Estado.BACKLOG,Estado.EM_ANDAMENTO));

        for (Tarefa t:tarefas){
            atualizacaoAutomaticaTarefa.verificarEAtualizarTarefa(t);
        }
    }

}
