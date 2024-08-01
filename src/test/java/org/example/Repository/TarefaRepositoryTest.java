package org.example.Repository;

import jakarta.persistence.EntityManager;
import org.example.Model.Categoria;
import org.example.Model.Tarefa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class TarefaRepositoryTest {

    @Autowired
    TarefaRepository tarefaRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Retorna true para a existencia da tarefa no BD")
    void existsByDescricaoSuccess() {
        Tarefa tarefa = new Tarefa();
        tarefa.setDescricao("Estudar BD");
        entityManager.persist(tarefa);

        boolean existsByDescricao = tarefaRepository.existsByDescricao("Estudar BD");

        assertTrue(existsByDescricao);
    }

    @Test
    @DisplayName("Retorna false para a existencia da tarefa no BD")
    void existsByDescricaoFail() {
        Tarefa tarefa = new Tarefa();
        tarefa.setDescricao("Estudar POO");
        entityManager.persist(tarefa);

        boolean existsByDescricao = this.tarefaRepository.existsByDescricao("Estudar");

        assertFalse(existsByDescricao);
    }

    @Test
    @DisplayName("Retorna true ao procurar tarefa com mesma descricao e id diferente")
    void existsByDescricaoAndIdNotSuccess() {
        Tarefa tarefa1 = new Tarefa();
        Tarefa tarefa2 = new Tarefa();
        String descricao = "Ler DDD";
        tarefa1.setDescricao(descricao);
        tarefa2.setDescricao(descricao);
        entityManager.persist(tarefa1);
        entityManager.persist(tarefa2);

        boolean existsByDescricaoAndIdNot = this.tarefaRepository
                .existsByDescricaoAndIdNot(tarefa1.getDescricao(),tarefa1.getId());

        assertTrue(existsByDescricaoAndIdNot);
    }

    @Test
    @DisplayName("Retorna false ao procurar tarefa com mesma descricao e id diferente")
    void existsByDescricaoAndIdNotFail() {
        Tarefa tarefa1 = new Tarefa();
        Tarefa tarefa2 = new Tarefa();
        tarefa1.setDescricao("Ler Clean Code");
        tarefa2.setDescricao("Ker");
        entityManager.persist(tarefa1);
        entityManager.persist(tarefa2);

        boolean existsByDescricaoAndIdNot = this.tarefaRepository
                .existsByDescricaoAndIdNot(tarefa1.getDescricao(),tarefa1.getId());

        assertFalse(existsByDescricaoAndIdNot);
    }

    @Test
    @DisplayName("Retorna false para isEmpty de tarefa por categoria")
    void findByCategoriaIdIsNotEmpty() {
        Categoria cat = new Categoria("Treino");
        entityManager.persist(cat);

        Tarefa tarefa = new Tarefa();
        tarefa.setCategoria(cat);
        entityManager.persist(tarefa);

        boolean isEmpty = this.tarefaRepository.findByCategoriaId(cat.getId()).isEmpty();

        assertFalse(isEmpty);
    }

    @Test
    @DisplayName("Retorna true para isEmpty de tarefa por categoria")
    void findByCategoriaIdIsEmpty() {
        Categoria cat1 = new Categoria("Treino");
        Categoria cat2 = new Categoria("Estudo");
        entityManager.persist(cat1);
        entityManager.persist(cat2);

        Tarefa tarefa = new Tarefa();
        tarefa.setCategoria(cat1);
        entityManager.persist(tarefa);

        boolean isEmpty = this.tarefaRepository.findByCategoriaId(cat2.getId()).isEmpty();

        assertTrue(isEmpty);
    }

    @Test
    @DisplayName("Retorna quantidade de tarefas na lista por categoria")
    void findByCategoriaIdIsEmptyNumber() {
        Categoria cat = new Categoria("Treino");
        entityManager.persist(cat);

        Tarefa tarefa1 = new Tarefa();
        Tarefa tarefa2 = new Tarefa();
        tarefa1.setCategoria(cat);
        tarefa2.setCategoria(cat);

        entityManager.persist(tarefa1);
        entityManager.persist(tarefa2);

        assertEquals(2,this.tarefaRepository.findByCategoriaId(cat.getId()).size());
    }
}