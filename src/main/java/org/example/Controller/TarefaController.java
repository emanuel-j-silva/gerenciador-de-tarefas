package org.example.Controller;

import jakarta.validation.Valid;
import org.example.DTO.TarefaDTO;
import org.example.Model.Categoria;
import org.example.Model.Estado;
import org.example.Model.Tarefa;
import org.example.Services.Categoria.FindCategoriaService;
import org.example.Services.Tarefa.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class TarefaController {
    @Autowired private SalvarTarefaService salvarTarefa;
    @Autowired private FindCategoriaService findCategoria;
    @Autowired private FindTarefaService findTarefa;
    @Autowired private ExcluirTarefaService excluirTarefa;
    @Autowired private AtualizarTarefaService atualizarTarefa;

    @PostMapping("/tarefas")
    public ResponseEntity<Tarefa> salvaTarefa(@RequestBody @Valid TarefaDTO tarefaDTO){
        var tarefa = new Tarefa();
        if (tarefaDTO.categoriaId() != null) {
            Categoria categoria = findCategoria.findById(tarefaDTO.categoriaId());
            if (categoria != null) tarefa.setCategoria(categoria);
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
            listTarefas = findTarefa.findTarefasByCategoria(categoriaId);
        } else{
            listTarefas = findTarefa.findAll();
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
        var tarefaO = findTarefa.findById(id);
        tarefaO.add(linkTo(TarefaController.class).slash("tarefas").withRel("Tarefas list"));

        return ResponseEntity.status(HttpStatus.OK).body(tarefaO);
    }

    @GetMapping("/tarefas/backlog")
    public ResponseEntity<List<Tarefa>> findAllBacklogTarefas(){
        var listBacklogTarefas = findTarefa.findTarefasByEstado(Estado.BACKLOG);
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
        var listEmAndamentoTarefas = findTarefa.findTarefasByEstado(Estado.EM_ANDAMENTO);
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
        var listAtrasadaTarefas = findTarefa.findTarefasByEstado(Estado.ATRASADA);
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
        var listConcluidaTarefas = findTarefa.findTarefasByEstado(Estado.CONCLUIDA);
        if (!listConcluidaTarefas.isEmpty()){
            for (Tarefa tarefa:listConcluidaTarefas){
                long id = tarefa.getId();
                tarefa.add(linkTo(methodOn(TarefaController.class).findOneTarefa(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(listConcluidaTarefas);
    }

    @PutMapping("/tarefas/{id}")
    public ResponseEntity<Object> atualizaTarefa(@PathVariable(value = "id") long id,
                                                  @RequestBody @Valid TarefaDTO tarefaDTO){

        var tarefa = findTarefa.findById(id);
        if (tarefaDTO.categoriaId() != null) {
            Categoria categoria = findCategoria.findById(tarefaDTO.categoriaId());
            if (categoria != null) tarefa.setCategoria(categoria);
        }
        if (tarefaDTO.estado() != null){
            BeanUtils.copyProperties(tarefaDTO,tarefa);
        }
        else {
            BeanUtils.copyProperties(tarefaDTO, tarefa, "estado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(atualizarTarefa.atualizar(tarefa));
    }

    @PatchMapping("/tarefas/{id}/categoria")
    public ResponseEntity<Object> atualizarCategoriaTarefa(
            @PathVariable(value = "id") Long tarefaId,
            @RequestParam(value = "categoriaId", required = false) Long categoriaId){

        return ResponseEntity.status(HttpStatus.OK).body(atualizarTarefa.atualizarCategoriaTarefa(tarefaId,categoriaId));
    }

    @DeleteMapping("/tarefas/{id}")
    public ResponseEntity<Object> deletarTarefa(@PathVariable(value = "id") long id){
        var tarefa = findTarefa.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(excluirTarefa.executar(tarefa));
    }
}
