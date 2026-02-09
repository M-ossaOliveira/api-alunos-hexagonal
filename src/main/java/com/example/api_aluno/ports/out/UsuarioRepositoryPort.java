package com.example.api_aluno.ports.out;

import com.example.api_aluno.domain.usuario.Usuario;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepositoryPort {
    Usuario salvar(Usuario usuario);
    Optional<Usuario> buscarPorId(UUID id);
    Optional<Usuario> buscarPorUsername(String username);
    boolean existsByUsername(String username);
}
