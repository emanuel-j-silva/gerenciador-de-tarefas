package org.example.Controller;

import jakarta.validation.Valid;
import org.example.DTO.TarefaDTO;
import org.example.Model.Categoria;
import org.example.Model.Estado;
import org.example.Model.Tarefa;
import org.example.Services.Categoria.FindCategoriaByIdService;
import org.example.Services.Tarefa.SalvarTarefaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class TarefaController {
    @Autowired SalvarTarefaService salvarTarefa;
    @Autowired FindCategoriaByIdService findCategoria;

    @PostMapping("/tarefas")
    public ResponseEntity<Tarefa> salvarTarefa(@RequestBody @Valid TarefaDTO tarefaDTO){
        var tarefa = new Tarefa();
        Optional<Categoria> categoria = Optional.ofNullable(findCategoria.executar(tarefaDTO.categoriaId()));
        if (!categoria.equals(null)) tarefa.setCategoria(categoria.get());
        if (tarefaDTO.estado() != null){
            BeanUtils.copyProperties(tarefaDTO,tarefa);
        }
        else {
            BeanUtils.copyProperties(tarefaDTO, tarefa, "estado");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(salvarTarefa.executar(tarefa));
    }
}
