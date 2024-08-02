package org.example.Services.Categoria;

import org.example.Model.Categoria;
import org.example.Repository.CategoriaRepository;
import org.example.Utils.CategoriaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindCategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria findById(long id){
        Categoria categoriaExistente = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException());

        return categoriaExistente;
    }

    public List<Categoria> findAll(){
        return categoriaRepository.findAll();
    }
}
