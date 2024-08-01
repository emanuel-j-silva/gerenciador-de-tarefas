package org.example.Repository;

import jakarta.persistence.EntityManager;
import org.example.DTO.CategoriaDTO;
import org.example.Model.Categoria;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class CategoriaRepositoryTest {

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Retorna true para a existência da categoria no BD")
    void existsByNomeSuccess() {
        CategoriaDTO data = new CategoriaDTO("Leitura");
        this.createCategoria(data);

        boolean existsByNome = this.categoriaRepository.existsByNome("Leitura");

        assertTrue(existsByNome);
    }

    @Test
    @DisplayName("Retorna false para a existência da categoria no BD")
    void existsByNomeFail() {
        CategoriaDTO data = new CategoriaDTO("Treino");
        this.createCategoria(data);

        boolean existsByNome = this.categoriaRepository.existsByNome("Test");

        assertFalse(existsByNome);
    }

    @Test
    @DisplayName("Retorna true ao procurar uma categoria com mesmo nome e id diferente")
    void existsByNomeAndIdNotSuccess() {
        CategoriaDTO data1 = new CategoriaDTO("Séries");
        Categoria cat1 = this.createCategoria(data1);
        Categoria cat2 = this.createCategoria(data1);

        boolean existsByNomeAndIdNot = this.categoriaRepository.existsByNomeAndIdNot(
                cat2.getNome(),cat2.getId());

        assertTrue(existsByNomeAndIdNot);
    }

    @Test
    @DisplayName("Retorna false ao criar categorias com nomes diferentes")
    void existsByNomeAndIdNotFail() {
        CategoriaDTO data1 = new CategoriaDTO("Jogo");
        CategoriaDTO data2 = new CategoriaDTO("Jogos");
        Categoria cat1 = this.createCategoria(data1);
        Categoria cat2 = this.createCategoria(data2);

        boolean existsByNomeAndIdNot = this.categoriaRepository.existsByNomeAndIdNot(
                cat2.getNome(),cat2.getId());

        assertFalse(existsByNomeAndIdNot);
    }
    private Categoria createCategoria(CategoriaDTO categoriaDTO){
        Categoria newCategoria = new Categoria(categoriaDTO.nome());
        this.entityManager.persist(newCategoria);
        return newCategoria;
    }
}