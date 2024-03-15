package org.example.DTO;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import org.example.Model.Categoria;
import org.example.Model.Estado;

import java.time.LocalDateTime;

public record TarefaDTO(@NotBlank String descricao, String notas,
                        @NotBlank LocalDateTime dataCriacao, @Future LocalDateTime dataVencimento,
                        Estado estado, Categoria categoria) {

}
