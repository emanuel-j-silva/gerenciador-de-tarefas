package org.example.Repository;

import org.example.Model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria,Long> {

    boolean existsByNome(String nome);
    boolean existsByNomeAndIdNot(String nome, Long id);
}
