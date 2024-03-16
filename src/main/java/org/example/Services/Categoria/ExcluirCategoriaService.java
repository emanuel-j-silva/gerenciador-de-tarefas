package org.example.Services.Categoria;

import org.example.Model.Categoria;
import org.example.Repository.CategoriaRepository;
import org.example.Utils.CategoriaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExcluirCategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    public String executar(Categoria categoria){
        if(!categoriaRepository.existsById(categoria.getId())){
            throw new CategoriaNotFoundException();
        }
        categoriaRepository.delete(categoria);
        return ("Categoria deletada com sucesso.");
    }
}
