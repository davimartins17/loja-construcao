package com.exemplo.loja.service;

import com.exemplo.loja.dto.ProdutoDtos.*;
import com.exemplo.loja.model.*;
import com.exemplo.loja.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class ProdutoService {
    private final ProdutoRepository produtos;
    private final CategoriaRepository categorias;
    private final FornecedorRepository fornecedores;
    public ProdutoService(ProdutoRepository produtos, CategoriaRepository categorias, FornecedorRepository fornecedores) {
        this.produtos = produtos; this.categorias = categorias; this.fornecedores = fornecedores;
    }
    public List<Response> listar() { return produtos.findAll().stream().map(this::toResponse).toList(); }
    public Response buscar(Long id) { return toResponse(produtos.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))); }
    public Response criar(Request dto) { Produto p = new Produto(); preencher(p,dto); return toResponse(produtos.save(p)); }
    public Response atualizar(Long id, Request dto) {
        Produto p = produtos.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        preencher(p,dto); return toResponse(produtos.save(p));
    }
    public void excluir(Long id) { produtos.deleteById(id); }
    private void preencher(Produto p, Request dto) {
        p.setNome(dto.nome()); p.setDescricao(dto.descricao()); p.setPreco(dto.preco());
        p.setQuantidadeEstoque(dto.quantidadeEstoque());
        p.setCategoria(categorias.findById(dto.categoriaId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não encontrada")));
        p.setFornecedor(dto.fornecedorId() == null ? null : fornecedores.findById(dto.fornecedorId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fornecedor não encontrado")));
    }
    private Response toResponse(Produto p) {
        return new Response(p.getId(), p.getNome(), p.getDescricao(), p.getPreco(), p.getQuantidadeEstoque(),
                p.getCategoria().getId(), p.getCategoria().getNome(),
                p.getFornecedor() == null ? null : p.getFornecedor().getId(),
                p.getFornecedor() == null ? null : p.getFornecedor().getNome());
    }
}
