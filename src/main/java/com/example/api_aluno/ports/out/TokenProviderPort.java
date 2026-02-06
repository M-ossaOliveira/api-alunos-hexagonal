package com.example.api_aluno.ports.out;

import com.example.api_aluno.domain.usuario.Usuario;

public interface TokenProviderPort {
    String generateToken(Usuario usuario);
    boolean validateToken(String token);
    String getUsernameFromToken(String token);
}