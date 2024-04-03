package org.example.Controller;

import jakarta.validation.Valid;
import org.example.DTO.TarefaDTO;
import org.example.Model.Categoria;
import org.example.Model.Tarefa;
import org.example.Services.Categoria.FindCategoriaByIdService;
import org.example.Services.Tarefa.FindAllTarefaService;
import org.example.Services.Tarefa.FindTarefaByIdService;
import org.example.Services.Tarefa.SalvarTarefaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class TarefaController {
    @Autowired SalvarTarefaService salvarTarefa;
    @Autowired FindCategoriaByIdService findCategoria;

    @Autowired FindTarefaByIdService findTarefaById;
    @Autowired FindAllTarefaService findAllTarefa;

    @PostMapping("/tarefas")
    public ResponseEntity<Tarefa> salvarTarefa(@RequestBody @Valid TarefaDTO tarefaDTO){
        var tarefa = new Tarefa();
        if (tarefaDTO.categoriaId() != null) {
            Optional<Categoria> categoria = Optional.ofNullable(findCategoria.executar(tarefaDTO.categoriaId()));
            if (!categoria.equals(null)) tarefa.setCategoria(categoria.get());
        }
        if (tarefaDTO.estado() != null){
            BeanUtils.copyProperties(tarefaDTO,tarefa);
        }
        else {
            BeanUtils.copyProperties(tarefaDTO, tarefa, "estado");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(salvarTarefa.executar(tarefa));
    }

    @GetMapping("/tarefas")
    public ResponseEntity<List<Tarefa>> findAllTarefas(){
        var listTarefas = findAllTarefa.executar();
        if (!listTarefas.isEmpty()){
            for (Tarefa tarefa:listTarefas){
                long id = tarefa.getId();
                tarefa.add(linkTo(methodOn(TarefaController.class).findOneTarefa(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(listTarefas);
    }

    @GetMapping("/tarefas/{id}")
    public ResponseEntity<Tarefa> findOneTarefa(@PathVariable(value = "id") long id){
        var tarefaO = findTarefaById.executar(id);
        tarefaO.add(linkTo(methodOn(TarefaController.class).findAllTarefas()).withRel("Tarefas list"));
        return ResponseEntity.status(HttpStatus.OK).body(tarefaO);
    }
}
