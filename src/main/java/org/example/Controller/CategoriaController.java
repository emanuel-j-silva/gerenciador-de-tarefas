package org.example.Controller;

import jakarta.validation.Valid;
import org.example.DTO.CategoriaDTO;
import org.example.Model.Categoria;
import org.example.Services.CriarCategoriaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoriaController {

    @Autowired
    CriarCategoriaService criarCategoria;

    @PostMapping("/categorias")
    public ResponseEntity<Categoria> salvarCategoria(@RequestBody @Valid CategoriaDTO categoriaDTO){
        var categoria = new Categoria();
        BeanUtils.copyProperties(categoriaDTO,categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(criarCategoria.executar(categoria));
    }
}
