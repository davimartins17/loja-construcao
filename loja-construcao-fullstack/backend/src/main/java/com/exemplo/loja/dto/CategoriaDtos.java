package com.exemplo.loja.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoriaDtos {
    public record Request(@NotBlank String nome, String descricao) {}
    public record Response(Long id, String nome, String descricao) {}
}
