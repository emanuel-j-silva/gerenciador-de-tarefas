package org.example.Services.Tarefa;

import org.example.Model.Tarefa;
import org.example.Repository.CategoriaRepository;
import org.example.Repository.TarefaRepository;
import org.example.Utils.CategoriaNotFoundException;
import org.example.Utils.TarefaCategoriaListIsEmptyException;
import org.example.Utils.TarefaListIsEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindTarefaByCategoriaService {

    @Autowired
    TarefaRepository tarefaRepository;
    @Autowired
    CategoriaRepository categoriaRepository;

    public List<Tarefa> executar(long categoriaId){
        if (tarefaRepository.findAll().isEmpty()){
            throw new TarefaListIsEmptyException();
        }
        if (!categoriaRepository.existsById(categoriaId)){
            throw new CategoriaNotFoundException();
        }
        List<Tarefa> tarefas = tarefaRepository.findByCategoriaId(categoriaId);
        if (tarefas.isEmpty()){
            throw new TarefaCategoriaListIsEmptyException();
        }
        return tarefas;
    }
}
