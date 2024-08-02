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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CategoriaController {
    @Autowired private SalvarCategoriaService salvarCategoria;
    @Autowired private AtualizarCategoriaService atualizarCategoria;
    @Autowired private ExcluirCategoriaService excluirCategoria;
    @Autowired private FindCategoriaService findCategoria;



    @PostMapping("/categorias")
    public ResponseEntity<Categoria> salvarCategoria(@RequestBody @Valid CategoriaDTO categoriaDTO){
        var categoria = new Categoria();
        BeanUtils.copyProperties(categoriaDTO,categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvarCategoria.executar(categoria));
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<Categoria>> findAllCategorias(){
        var listCategorias = findCategoria.findAll();

        if (listCategorias.isEmpty())
            return ResponseEntity.noContent().build();

        for(Categoria categoria:listCategorias){
            long id = categoria.getId();
            categoria.add(linkTo(methodOn(CategoriaController.class).findOneCategoria(id)).withSelfRel());
        }

        return ResponseEntity.status(HttpStatus.OK).body(listCategorias);
    }

    @GetMapping("/categorias/{id}")
    public ResponseEntity<Categoria> findOneCategoria(@PathVariable(value = "id") long id){
        var categoriaO = findCategoria.findById(id);
        categoriaO.add(linkTo(methodOn(CategoriaController.class).findAllCategorias()).withRel("Categorias list"));
        return ResponseEntity.status(HttpStatus.OK).body(categoriaO);
    }

    @PutMapping("/categorias/{id}")
    public ResponseEntity<Object> atualizarCategoria(@PathVariable(value = "id") long id,
                                                     @RequestBody @Valid CategoriaDTO categoriaDTO){
        var categoria = findCategoria.findById(id);
        BeanUtils.copyProperties(categoriaDTO,categoria);
        return ResponseEntity.status(HttpStatus.OK).body(atualizarCategoria.executar(categoria));
    }

    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<Object> deletarCategoria(@PathVariable(value = "id") long id){
        var categoria = findCategoria.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(excluirCategoria.executar(categoria));
    }
}
