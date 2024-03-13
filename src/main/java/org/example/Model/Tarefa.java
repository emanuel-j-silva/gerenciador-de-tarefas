package org.example.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tarefas")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String descricao;
    private String notas;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataVencimento;

    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.BACKLOG;

    @ManyToOne
    private Categoria categoria;




}
