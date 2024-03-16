package org.example.Controller;

import jakarta.validation.Valid;
import org.example.DTO.CategoriaDTO;
import org.example.Model.Categoria;
import org.example.Services.Categoria.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoriaController {
    @Autowired SalvarCategoriaService salvarCategoria;
    @Autowired FindCategoriaByIdService findCategoriaById;
    @Autowired FindAllCategoriaService findAll;
    @Autowired AtualizarCategoriaService atualizarCategoria;
    @Autowired ExcluirCategoriaService excluirCategoria;



    @PostMapping("/categorias")
    public ResponseEntity<Categoria> salvarCategoria(@RequestBody @Valid CategoriaDTO categoriaDTO){
        var categoria = new Categoria();
        BeanUtils.copyProperties(categoriaDTO,categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvarCategoria.executar(categoria));
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<Categoria>> findAllCategorias(){
        return ResponseEntity.status(HttpStatus.OK).body(findAll.executar());
    }

    @GetMapping("/categorias/{id}")
    public ResponseEntity<Categoria> findOneCategoria(@PathVariable(value = "id") long id){
        return ResponseEntity.status(HttpStatus.OK).body(findCategoriaById.executar(id));
    }

    @PutMapping("/categorias/{id}")
    public ResponseEntity<Object> atualizarCategoria(@PathVariable(value = "id") long id,
                                                     @RequestBody @Valid CategoriaDTO categoriaDTO){
        var categoria = findCategoriaById.executar(id);
        BeanUtils.copyProperties(categoriaDTO,categoria);
        return ResponseEntity.status(HttpStatus.OK).body(atualizarCategoria.executar(categoria));
    }

    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<Object> deletarCategoria(@PathVariable(value = "id") long id){
        var categoria = findCategoriaById.executar(id);
        return ResponseEntity.status(HttpStatus.OK).body(excluirCategoria.executar(categoria));
    }
}
