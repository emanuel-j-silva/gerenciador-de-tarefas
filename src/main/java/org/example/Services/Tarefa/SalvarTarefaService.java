package org.example.Services.Tarefa;

import org.example.Model.Tarefa;
import org.example.Repository.CategoriaRepository;
import org.example.Repository.TarefaRepository;
import org.example.Utils.CategoriaNotFoundException;
import org.example.Utils.TarefaExistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalvarTarefaService {

    @Autowired
    TarefaRepository tarefaRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    public Tarefa executar(Tarefa tarefa){
       if (tarefaRepository.existsByDescricao(tarefa.getDescricao())){
           throw new TarefaExistenteException(tarefa.getDescricao());
       }
       if (!categoriaRepository.existsById(tarefa.getCategoria().getId())
               && tarefa.getCategoria() != null){
           throw new CategoriaNotFoundException();
       }
       return tarefaRepository.save(tarefa);
    }
}
