# Documentação da API

Swagger UI: `http://localhost:8080/swagger-ui/index.html`

## Autenticação
### POST `/api/auth/cadastro`
```json
{ "nome": "Davi", "email": "davi@email.com", "senha": "senha1234" }
```
Resposta: token JWT, nome, email e papel.

### POST `/api/auth/login`
```json
{ "email": "davi@email.com", "senha": "senha1234" }
```
Enviar o token nas rotas protegidas:
`Authorization: Bearer SEU_TOKEN`

## Categorias
- `GET /api/categorias` — listar (público)
- `POST /api/categorias` — criar (autenticado)
- `PUT /api/categorias/{id}` — atualizar (autenticado)
- `DELETE /api/categorias/{id}` — excluir (autenticado)

Corpo:
```json
{ "nome": "Ferramentas", "descricao": "Ferramentas manuais e elétricas" }
```

## Produtos
- `GET /api/produtos` — listar (público)
- `GET /api/produtos/{id}` — buscar (público)
- `POST /api/produtos` — criar (autenticado)
- `PUT /api/produtos/{id}` — atualizar (autenticado)
- `DELETE /api/produtos/{id}` — excluir (autenticado)

Corpo:
```json
{
  "nome": "Furadeira 650W",
  "descricao": "Furadeira de impacto",
  "preco": 299.90,
  "quantidadeEstoque": 12,
  "categoriaId": 1,
  "fornecedorId": null
}
```

## Fornecedores
- `GET /api/fornecedores` — listar (autenticado)
- `POST /api/fornecedores` — criar (autenticado)
- `PUT /api/fornecedores/{id}` — atualizar (autenticado)
- `DELETE /api/fornecedores/{id}` — excluir (autenticado)

Corpo:
```json
{ "nome": "Distribuidora ABC", "cnpj": "00000000000100", "telefone": "48999990000", "email": "contato@abc.com" }
```

## Códigos HTTP comuns
- `200`: consulta/atualização bem-sucedida.
- `201`: recurso criado (a implementação pode responder 200 por padrão).
- `400`: validação ou referência inválida.
- `401`: não autenticado/credenciais inválidas.
- `404`: recurso não encontrado.
- `409`: e-mail já cadastrado.
