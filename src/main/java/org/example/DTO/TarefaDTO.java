package org.example.DTO;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import org.example.Model.Estado;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

public record TarefaDTO(@NotBlank String descricao, String notas,
                        @FutureOrPresent LocalDateTime dataVencimento,
                        Estado estado, Long categoriaId) {

}

