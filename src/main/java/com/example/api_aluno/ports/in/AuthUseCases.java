package com.example.api_aluno.ports.in;

import com.example.api_aluno.domain.usuario.Perfil;
import com.example.api_aluno.domain.usuario.Usuario;

import java.util.Optional;
import java.util.Set;

public interface AuthUseCases {
    Usuario registrar(String username, String senhaPura, Set<Perfil> perfis);
    String login(String username, String senhaPura);
    Optional<Usuario> buscarPorUsername(String username);
}
