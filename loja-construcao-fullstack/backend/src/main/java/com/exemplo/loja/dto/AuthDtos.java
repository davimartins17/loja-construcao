package com.exemplo.loja.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthDtos {
    public record CadastroRequest(@NotBlank String nome, @Email @NotBlank String email,
                                  @Size(min = 8) String senha) {}
    public record LoginRequest(@Email @NotBlank String email, @NotBlank String senha) {}
    public record AuthResponse(String token, String nome, String email, String papel) {}
}
