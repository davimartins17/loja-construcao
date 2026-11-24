package com.exemplo.loja.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class ProdutoDtos {
    public record Request(@NotBlank String nome, String descricao,
                          @NotNull @DecimalMin("0.01") BigDecimal preco,
                          @NotNull @Min(0) Integer quantidadeEstoque,
                          @NotNull Long categoriaId, Long fornecedorId) {}
    public record Response(Long id, String nome, String descricao, BigDecimal preco,
                           Integer quantidadeEstoque, Long categoriaId, String categoria,
                           Long fornecedorId, String fornecedor) {}
}
