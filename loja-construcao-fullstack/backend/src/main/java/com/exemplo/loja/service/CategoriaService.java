package com.exemplo.loja.service;

import com.exemplo.loja.dto.CategoriaDtos.*;
import com.exemplo.loja.model.Categoria;
import com.exemplo.loja.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoriaService {
    private final CategoriaRepository repo;
    public CategoriaService(CategoriaRepository repo) { this.repo = repo; }
    public List<Response> listar() { return repo.findAll().stream().map(this::toResponse).toList(); }
    public Response criar(Request dto) {
        Categoria c = new Categoria(); c.setNome(dto.nome()); c.setDescricao(dto.descricao());
        return toResponse(repo.save(c));
    }
    public Response atualizar(Long id, Request dto) {
        Categoria c = repo.findById(id).orElseThrow();
        c.setNome(dto.nome()); c.setDescricao(dto.descricao());
        return toResponse(repo.save(c));
    }
    public void excluir(Long id) { repo.deleteById(id); }
    private Response toResponse(Categoria c) { return new Response(c.getId(), c.getNome(), c.getDescricao()); }
}
