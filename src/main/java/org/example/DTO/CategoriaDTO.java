package org.example.DTO;

import jakarta.validation.constraints.NotBlank;

public record CategoriaDTO(@NotBlank String nome) {
}
