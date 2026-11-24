package com.exemplo.loja.controller;

import com.exemplo.loja.dto.ProdutoDtos.*;
import com.exemplo.loja.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    private final ProdutoService service;
    public ProdutoController(ProdutoService service) { this.service = service; }
    @GetMapping public List<Response> listar() { return service.listar(); }
    @GetMapping("/{id}") public Response buscar(@PathVariable Long id) { return service.buscar(id); }
    @PostMapping public Response criar(@Valid @RequestBody Request dto) { return service.criar(dto); }
    @PutMapping("/{id}") public Response atualizar(@PathVariable Long id, @Valid @RequestBody Request dto) { return service.atualizar(id,dto); }
    @DeleteMapping("/{id}") public void excluir(@PathVariable Long id) { service.excluir(id); }
}
