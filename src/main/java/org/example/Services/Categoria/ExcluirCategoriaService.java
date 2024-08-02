package org.example.Services.Categoria;

import org.example.Model.Categoria;
import org.example.Model.Tarefa;
import org.example.Repository.CategoriaRepository;
import org.example.Repository.TarefaRepository;
import org.example.Utils.CategoriaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExcluirCategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private TarefaRepository tarefaRepository;

    public String executar(Categoria categoria){
        if(!categoriaRepository.existsById(categoria.getId())){
            throw new CategoriaNotFoundException();
        }
        List<Tarefa> tarefas = tarefaRepository.findByCategoriaId(categoria.getId());
        tarefas.forEach(tarefa -> {
            tarefa.setCategoria(null);
            tarefaRepository.save(tarefa);
        });
        categoriaRepository.delete(categoria);
        return ("Categoria deletada com sucesso.");
    }
}
