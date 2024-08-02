package org.example.Services.Categoria;

import org.example.Model.Categoria;
import org.example.Repository.CategoriaRepository;
import org.example.Utils.CategoriaExistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalvarCategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria executar(Categoria categoria){
        if (categoriaRepository.existsByNome(categoria.getNome())){
            throw new CategoriaExistenteException(categoria.getNome());
        }
        return categoriaRepository.save(categoria);
    }
}
