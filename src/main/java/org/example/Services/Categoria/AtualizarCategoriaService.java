package org.example.Services.Categoria;

import org.example.Model.Categoria;
import org.example.Repository.CategoriaRepository;
import org.example.Utils.CategoriaExistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AtualizarCategoriaService {
    @Autowired
    CategoriaRepository categoriaRepository;

    public Categoria executar(Categoria categoria){
        if (categoriaRepository.existsByNomeAndIdNot(categoria.getNome(), categoria.getId())){
            throw new CategoriaExistenteException(categoria.getNome());
        }
        return categoriaRepository.save(categoria);
    }
}
