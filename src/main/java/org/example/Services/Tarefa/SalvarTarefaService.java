package org.example.Services.Tarefa;

import org.example.Model.Estado;
import org.example.Model.Tarefa;
import org.example.Repository.TarefaRepository;
import org.example.Utils.TarefaEstadoCriacaoException;
import org.example.Utils.TarefaExistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalvarTarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private AgendarTarefaService agendarTarefa;

    public Tarefa executar(Tarefa tarefa){
       if (tarefaRepository.existsByDescricao(tarefa.getDescricao())){
           throw new TarefaExistenteException(tarefa.getDescricao());
       }
       if (tarefa.getEstado().equals(Estado.ATRASADA)){
           throw new TarefaEstadoCriacaoException();
       }

        Tarefa savedTarefa = tarefaRepository.save(tarefa);
       agendarTarefa.agendarVerificacaoTarefa(savedTarefa);
        return savedTarefa;
    }
}
