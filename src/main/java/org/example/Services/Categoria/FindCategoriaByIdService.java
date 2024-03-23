package org.example.Services.Categoria;

import org.example.Model.Categoria;
import org.example.Repository.CategoriaRepository;
import org.example.Utils.CategoriaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindCategoriaByIdService {

    @Autowired
    CategoriaRepository categoriaRepository;

    public Categoria executar(Long id){
        Categoria categoriaExistente = categoriaRepository.findById(id)
                .orElseThrow(()->new CategoriaNotFoundException());
        return categoriaExistente;
    }
}
