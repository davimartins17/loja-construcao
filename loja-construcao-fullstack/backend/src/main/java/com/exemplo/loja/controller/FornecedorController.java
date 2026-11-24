package com.exemplo.loja.controller;

import com.exemplo.loja.model.Fornecedor;
import com.exemplo.loja.repository.FornecedorRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/fornecedores")
public class FornecedorController {
    public record FornecedorRequest(@NotBlank String nome, String cnpj, String telefone, String email) {}
    public record FornecedorResponse(Long id, String nome, String cnpj, String telefone, String email) {}
    private final FornecedorRepository repo;
    public FornecedorController(FornecedorRepository repo) { this.repo = repo; }
    @GetMapping public List<FornecedorResponse> listar() { return repo.findAll().stream().map(this::map).toList(); }
    @PostMapping public FornecedorResponse criar(@Valid @RequestBody FornecedorRequest dto) {
        Fornecedor f = new Fornecedor(); preencher(f,dto); return map(repo.save(f));
    }
    @PutMapping("/{id}") public FornecedorResponse atualizar(@PathVariable Long id, @Valid @RequestBody FornecedorRequest dto) {
        Fornecedor f = repo.findById(id).orElseThrow(); preencher(f,dto); return map(repo.save(f));
    }
    @DeleteMapping("/{id}") public void excluir(@PathVariable Long id) { repo.deleteById(id); }
    private void preencher(Fornecedor f, FornecedorRequest d) { f.setNome(d.nome()); f.setCnpj(d.cnpj()); f.setTelefone(d.telefone()); f.setEmail(d.email()); }
    private FornecedorResponse map(Fornecedor f) { return new FornecedorResponse(f.getId(),f.getNome(),f.getCnpj(),f.getTelefone(),f.getEmail()); }
}
