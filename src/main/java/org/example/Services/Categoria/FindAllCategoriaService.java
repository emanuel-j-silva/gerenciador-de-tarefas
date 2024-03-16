package org.example.Services.Categoria;

import org.example.Model.Categoria;
import org.example.Repository.CategoriaRepository;
import org.example.Utils.CategoriaListIsEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllCategoriaService {
    @Autowired
    CategoriaRepository categoriaRepository;

    public List<Categoria> executar(){
        List<Categoria> categorias = categoriaRepository.findAll();
        if (categorias.isEmpty()){
            throw new CategoriaListIsEmptyException();
        }
        return categorias;
    }
}
