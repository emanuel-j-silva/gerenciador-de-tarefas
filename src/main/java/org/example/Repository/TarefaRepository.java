package org.example.Repository;

import org.example.Model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa,Long> {

    boolean existsByDescricao(String descricao);
    boolean existsByDescricaoAndIdNot(String descricao, Long id);
    List<Tarefa> findByCategoriaId(Long categoriaId);

}
