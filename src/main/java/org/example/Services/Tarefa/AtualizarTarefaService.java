package org.example.Services.Tarefa;

import org.example.Model.Categoria;
import org.example.Model.Estado;
import org.example.Model.Tarefa;
import org.example.Repository.CategoriaRepository;
import org.example.Repository.TarefaRepository;
import org.example.Utils.CategoriaNotFoundException;
import org.example.Utils.TarefaExistenteException;
import org.example.Utils.TarefaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AtualizarTarefaService {
    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private AgendarTarefaService agendarTarefa;

    public Tarefa atualizar(Tarefa tarefa){
        Tarefa oldTarefa = tarefaRepository.findById(tarefa.getId())
                .orElseThrow(TarefaNotFoundException::new);

        if (!validEstado(oldTarefa,tarefa.getEstado()))
            throw new IllegalArgumentException("Inconsistência ao atualizar estado.");

        if (tarefaRepository.existsByDescricaoAndIdNot(tarefa.getDescricao(), tarefa.getId()))
            throw new TarefaExistenteException(tarefa.getDescricao());

        Tarefa updatedTarefa = tarefaRepository.save(tarefa);
        agendarTarefa.agendarVerificacaoTarefa(updatedTarefa);
        return updatedTarefa;
    }

    public Tarefa atualizarCategoriaTarefa(Long tarefaId, Long categoriaId) {
        Tarefa tarefa = tarefaRepository.findById(tarefaId)
                .orElseThrow(TarefaNotFoundException::new);

        if (categoriaId != null) {
            Categoria categoria = categoriaRepository.findById(categoriaId)
                    .orElseThrow(CategoriaNotFoundException::new);
            tarefa.setCategoria(categoria);
        } else {
            tarefa.setCategoria(null);  // Remove a categoria
        }
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
