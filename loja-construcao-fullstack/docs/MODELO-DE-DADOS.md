# Modelo de dados

## Entidades e relacionamentos
- `usuarios` 1:N `pedidos`: um usuário pode registrar vários pedidos.
- `categorias` 1:N `produtos`: cada produto pertence a uma categoria.
- `fornecedores` 1:N `produtos`: um fornecedor pode fornecer vários produtos; fornecedor é opcional.
- `pedidos` 1:N `itens_pedido`: um pedido possui vários itens.
- `produtos` 1:N `itens_pedido`: um produto pode aparecer em vários itens de pedidos.

```mermaid
erDiagram
  USUARIOS ||--o{ PEDIDOS : registra
  PEDIDOS ||--|{ ITENS_PEDIDO : contem
  PRODUTOS ||--o{ ITENS_PEDIDO : compoe
  CATEGORIAS ||--o{ PRODUTOS : classifica
  FORNECEDORES ||--o{ PRODUTOS : fornece
  USUARIOS {
    bigint id PK
    varchar nome
    varchar email UK
    varchar senha
    varchar papel
  }
  CATEGORIAS {
    bigint id PK
    varchar nome UK
    varchar descricao
  }
  FORNECEDORES {
    bigint id PK
    varchar nome
    varchar cnpj UK
    varchar telefone
    varchar email
  }
  PRODUTOS {
    bigint id PK
    varchar nome
    decimal preco
    int quantidade_estoque
    bigint categoria_id FK
    bigint fornecedor_id FK
  }
  PEDIDOS {
    bigint id PK
    bigint usuario_id FK
    timestamp data
    varchar status
    decimal valor_total
  }
  ITENS_PEDIDO {
    bigint id PK
    bigint pedido_id FK
    bigint produto_id FK
    int quantidade
    decimal preco_unitario
  }
```

## Decisões
- Valores monetários usam `BigDecimal`, evitando imprecisão de ponto flutuante.
- Relações `ManyToOne` são donas das chaves estrangeiras.
- `ItemPedido.precoUnitario` registra o preço no momento da venda, preservando o histórico.
- O status do pedido é enum: `PENDENTE`, `CONFIRMADO`, `CANCELADO`.
- O cadastro público não permite escolher papel: todo usuário novo recebe `FUNCIONARIO`.
