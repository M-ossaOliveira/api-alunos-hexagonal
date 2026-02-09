package com.example.api_aluno.application;

import com.example.api_aluno.domain.usuario.Perfil;
import com.example.api_aluno.domain.usuario.Usuario;
import com.example.api_aluno.ports.in.AuthUseCases;
import com.example.api_aluno.ports.out.PasswordEncoderPort;
import com.example.api_aluno.ports.out.TokenProviderPort;
import com.example.api_aluno.ports.out.UsuarioRepositoryPort;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/** Implementa os casos de uso de autenticação definidos em AuthUseCases
 * Orquestra as regras da aplicação e delega as chamadas para as ports de saída:
 * -UsuarioRepositoryPort (persistência)
 * -PasswordEncoderPort (hash/validação de senha)
 * -TokenProviderPort(gerar/validar/parse de Token)
 * É chamado pelos controllers de auth*/
public class UsuarioService implements AuthUseCases {
    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final TokenProviderPort tokenProviderPort;
    public UsuarioService(UsuarioRepositoryPort usuarioRepositoryPort,
                          PasswordEncoderPort passwordEncoderPort,
                          TokenProviderPort tokenProviderPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.tokenProviderPort = tokenProviderPort;
    }
    @Override
    public Usuario registrar(String username, String senhaPura, Set<Perfil> perfis) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("username é obrigatório");
        }
        if (senhaPura == null || senhaPura.isBlank()) {
            throw new IllegalArgumentException("senha é obrigatória");
        }
        if (usuarioRepositoryPort.existsByUsername(username)) {
            throw new IllegalStateException("username já está em uso");
        }
        String hash = passwordEncoderPort.encode(senhaPura);
        Usuario novo = new Usuario(UUID.randomUUID(), username, hash, perfis);
        return usuarioRepositoryPort.salvar(novo);
    }
    @Override
    public String login(String username, String senhaPura) {
        Optional<Usuario> opt = usuarioRepositoryPort.buscarPorUsername(username);
        Usuario usuario = opt.orElseThrow(() -> new IllegalArgumentException("credenciais inválidas"));
        if (!passwordEncoderPort.matches(senhaPura, usuario.getSenhaHash())) {
            throw new IllegalArgumentException("credenciais inválidas");
        }
        return tokenProviderPort.generateToken(usuario);
    }
    @Override
    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepositoryPort.buscarPorUsername(username);
    }
}

