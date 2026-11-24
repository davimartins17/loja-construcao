package com.exemplo.loja.controller;

import com.exemplo.loja.dto.CategoriaDtos.*;
import com.exemplo.loja.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
    private final CategoriaService service;
    public CategoriaController(CategoriaService service) { this.service = service; }
    @GetMapping public List<Response> listar() { return service.listar(); }
    @PostMapping public Response criar(@Valid @RequestBody Request dto) { return service.criar(dto); }
    @PutMapping("/{id}") public Response atualizar(@PathVariable Long id, @Valid @RequestBody Request dto) { return service.atualizar(id, dto); }
    @DeleteMapping("/{id}") public void excluir(@PathVariable Long id) { service.excluir(id); }
}
