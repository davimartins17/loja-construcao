# Roteiro de testes manuais

1. Subir o banco e backend.
2. Criar conta pela tela de login/cadastro.
3. Confirmar que a resposta contém JWT e papel `FUNCIONARIO`.
4. Cadastrar categoria; listar, atualizar e excluir.
5. Cadastrar fornecedor pela API Swagger.
6. Cadastrar produto relacionado a categoria e fornecedor.
7. Conferir que o produto aparece no dashboard e na listagem.
8. Tentar criar produto com `categoriaId` inexistente: deve retornar erro 400.
9. Fazer logout e tentar abrir uma rota protegida: deve redirecionar ao login.
10. Fazer login novamente e confirmar que o Axios envia o Bearer token.

## Próximas evoluções sugeridas
- Implementar pedidos e itens com transação e baixa de estoque.
- Implementar movimentações de estoque (entrada/saída/ajuste) com histórico.
- Criar autorização por papel usando `@PreAuthorize`.
- Adicionar paginação, filtros e ordenação.
- Adicionar Flyway para versionar o esquema do banco.
- Criar testes unitários e de integração com Testcontainers.
