package com.exemplo.loja.controller;

import com.exemplo.loja.dto.AuthDtos.*;
import com.exemplo.loja.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService service;
    public AuthController(AuthService service) { this.service = service; }
    @PostMapping("/cadastro") public AuthResponse cadastrar(@Valid @RequestBody CadastroRequest dto) { return service.cadastrar(dto); }
    @PostMapping("/login") public AuthResponse login(@Valid @RequestBody LoginRequest dto) { return service.login(dto); }
}
