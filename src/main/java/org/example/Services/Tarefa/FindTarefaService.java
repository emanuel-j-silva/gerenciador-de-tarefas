package org.example.Services.Tarefa;

import org.example.Model.Categoria;
import org.example.Model.Estado;
import org.example.Model.Tarefa;
import org.example.Repository.CategoriaRepository;
import org.example.Repository.TarefaRepository;
import org.example.Utils.CategoriaNotFoundException;
import org.example.Utils.TarefaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindTarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Tarefa findById(long id){
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(()-> new TarefaNotFoundException());

        return tarefa;
    }

    public List<Tarefa> findAll(){
        return tarefaRepository.findAll();
    }

    public List<Tarefa> findTarefasByEstado(Estado estado){
        return tarefaRepository.findAll().stream()
                .filter(tarefa -> tarefa.getEstado() == estado)
                .collect(Collectors.toList());
    }

    public List<Tarefa> findTarefasByCategoria(long categoriaId){
        if (!categoriaRepository.existsById(categoriaId)){
            throw new CategoriaNotFoundException();
        }

        return tarefaRepository.findByCategoriaId(categoriaId);
    }
}
