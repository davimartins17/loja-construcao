package com.exemplo.loja.service;

import com.exemplo.loja.dto.AuthDtos.*;
import com.exemplo.loja.model.*;
import com.exemplo.loja.repository.UsuarioRepository;
import com.exemplo.loja.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {
    private final UsuarioRepository usuarios;
    private final PasswordEncoder encoder;
    private final JwtService jwt;
    public AuthService(UsuarioRepository usuarios, PasswordEncoder encoder, JwtService jwt) {
        this.usuarios = usuarios; this.encoder = encoder; this.jwt = jwt;
    }
    @Transactional
    public AuthResponse cadastrar(CadastroRequest dto) {
        if (usuarios.existsByEmail(dto.email())) throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já cadastrado");
        Usuario u = new Usuario();
        u.setNome(dto.nome()); u.setEmail(dto.email());
        u.setSenha(encoder.encode(dto.senha()));
        u.setPapel(Papel.FUNCIONARIO); // papel nunca vem do cadastro público
        usuarios.save(u);
        return new AuthResponse(jwt.gerar(u.getEmail()), u.getNome(), u.getEmail(), u.getPapel().name());
    }
    public AuthResponse login(LoginRequest dto) {
        Usuario u = usuarios.findByEmail(dto.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas"));
        if (!encoder.matches(dto.senha(), u.getSenha()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas");
        return new AuthResponse(jwt.gerar(u.getEmail()), u.getNome(), u.getEmail(), u.getPapel().name());
    }
}
