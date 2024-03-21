package org.example.Controller;

import jakarta.validation.Valid;
import org.example.DTO.TarefaDTO;
import org.example.Model.Tarefa;
import org.example.Services.Tarefa.SalvarTarefaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TarefaController {
    @Autowired SalvarTarefaService salvarTarefa;

    @PostMapping("/tarefas")
    public ResponseEntity<Tarefa> salvarTarefa(@RequestBody @Valid TarefaDTO tarefaDTO){
        var tarefa = new Tarefa();
        if (tarefaDTO.estado() != null){
            BeanUtils.copyProperties(tarefaDTO,tarefa);
        }
        else {
            BeanUtils.copyProperties(tarefaDTO, tarefa, "estado");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(salvarTarefa.executar(tarefa));
    }
}
