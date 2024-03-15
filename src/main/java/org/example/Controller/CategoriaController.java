package org.example.Controller;

import jakarta.validation.Valid;
import org.example.DTO.CategoriaDTO;
import org.example.Model.Categoria;
import org.example.Services.Categoria.AtualizarCategoriaService;
import org.example.Services.Categoria.SalvarCategoriaService;
import org.example.Services.Categoria.FindCategoriaByIdService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoriaController {
    @Autowired SalvarCategoriaService salvarCategoria;
    @Autowired FindCategoriaByIdService findCategoriaById;
    @Autowired AtualizarCategoriaService atualizarCategoria;



    @PostMapping("/categorias")
    public ResponseEntity<Categoria> salvarCategoria(@RequestBody @Valid CategoriaDTO categoriaDTO){
        var categoria = new Categoria();
        BeanUtils.copyProperties(categoriaDTO,categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvarCategoria.executar(categoria));
    }

    @PutMapping("/categorias/{id}")
    public ResponseEntity<Object> atualizarCategoria(@PathVariable(value = "id") long id,
                                                     @RequestBody @Valid CategoriaDTO categoriaDTO){
        var categoria = findCategoriaById.executar(id);
        BeanUtils.copyProperties(categoriaDTO,categoria);
        return ResponseEntity.status(HttpStatus.OK).body(atualizarCategoria.executar(categoria));
    }
}
