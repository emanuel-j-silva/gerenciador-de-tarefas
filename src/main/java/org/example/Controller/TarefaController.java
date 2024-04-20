package org.example.Controller;

import jakarta.validation.Valid;
import org.example.DTO.TarefaDTO;
import org.example.Model.Categoria;
import org.example.Model.Estado;
import org.example.Model.Tarefa;
import org.example.Services.Categoria.FindCategoriaByIdService;
import org.example.Services.Tarefa.*;
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
    @Autowired FindTarefasByEstadoService findTarefasByEstado;
    @Autowired FindTarefaByCategoriaService findTarefasByCategoria;

    @Autowired ExcluirTarefaService excluirTarefa;

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
    public ResponseEntity<List<Tarefa>> findAllTarefas(
            @RequestParam(value = "categoria",required = false) Long categoriaId){
        List<Tarefa> listTarefas;
        if (categoriaId != null){
            listTarefas = findTarefasByCategoria.executar(categoriaId);
        } else{
            listTarefas = findAllTarefa.executar();
        }
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
        tarefaO.add(linkTo(TarefaController.class).slash("tarefas").withRel("Tarefas list"));

        return ResponseEntity.status(HttpStatus.OK).body(tarefaO);
    }

    @GetMapping("/tarefas/backlog")
    public ResponseEntity<List<Tarefa>> findAllBacklogTarefas(){
        var listBacklogTarefas = findTarefasByEstado.executar(Estado.BACKLOG);
        if (!listBacklogTarefas.isEmpty()){
            for (Tarefa tarefa:listBacklogTarefas){
                long id = tarefa.getId();
                tarefa.add(linkTo(methodOn(TarefaController.class).findOneTarefa(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(listBacklogTarefas);
    }

    @GetMapping("/tarefas/em-andamento")
    public ResponseEntity<List<Tarefa>> findAllEmAndamentoTarefas(){
        var listEmAndamentoTarefas = findTarefasByEstado.executar(Estado.EM_ANDAMENTO);
        if (!listEmAndamentoTarefas.isEmpty()){
            for (Tarefa tarefa:listEmAndamentoTarefas){
                long id = tarefa.getId();
                tarefa.add(linkTo(methodOn(TarefaController.class).findOneTarefa(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(listEmAndamentoTarefas);
    }

    @GetMapping("/tarefas/atrasadas")
    public ResponseEntity<List<Tarefa>> findAllAtrasadaTarefas(){
        var listAtrasadaTarefas = findTarefasByEstado.executar(Estado.ATRASADA);
        if (!listAtrasadaTarefas.isEmpty()){
            for (Tarefa tarefa:listAtrasadaTarefas){
                long id = tarefa.getId();
                tarefa.add(linkTo(methodOn(TarefaController.class).findOneTarefa(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(listAtrasadaTarefas);
    }

    @GetMapping("/tarefas/concluidas")
    public ResponseEntity<List<Tarefa>> findAllConcluidaTarefas(){
        var listConcluidaTarefas = findTarefasByEstado.executar(Estado.CONCLUIDA);
        if (!listConcluidaTarefas.isEmpty()){
            for (Tarefa tarefa:listConcluidaTarefas){
                long id = tarefa.getId();
                tarefa.add(linkTo(methodOn(TarefaController.class).findOneTarefa(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(listConcluidaTarefas);
    }



    @DeleteMapping("/tarefas/{id}")
    public ResponseEntity<Object> deletarTarefa(@PathVariable(value = "id") long id){
        var tarefa = findTarefaById.executar(id);
        return ResponseEntity.status(HttpStatus.OK).body(excluirTarefa.executar(tarefa));
    }
}
