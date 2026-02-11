package com.example.api_aluno.application;

import com.example.api_aluno.domain.usuario.Perfil;
import com.example.api_aluno.domain.usuario.Usuario;
import com.example.api_aluno.ports.out.PasswordEncoderPort;
import com.example.api_aluno.ports.out.TokenProviderPort;
import com.example.api_aluno.ports.out.UsuarioRepositoryPort;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceUnitTest {

    @Test
    void registrar_ok_quando_username_disponivel() {
        UsuarioRepositoryPort repo = mock(UsuarioRepositoryPort.class);
        PasswordEncoderPort enc = mock(PasswordEncoderPort.class);
        TokenProviderPort tok = mock(TokenProviderPort.class);

        when(repo.existsByUsername("mari")).thenReturn(false);
        when(enc.encode("123456")).thenReturn("hash");
        when(repo.salvar(any())).thenAnswer(inv -> inv.getArgument(0));

        UsuarioService service = new UsuarioService(repo, enc, tok);

        Usuario u = service.registrar("mari", "123456", Set.of(Perfil.ROLE_USER));

        assertNotNull(u.getId());
        assertEquals("mari", u.getUsername());
        assertEquals("hash", u.getSenhaHash());
        assertTrue(u.getPerfis().contains(Perfil.ROLE_USER));
    }

    @Test
    void registrar_rejeita_username_duplicado() {
        UsuarioRepositoryPort repo = mock(UsuarioRepositoryPort.class);
        PasswordEncoderPort enc = mock(PasswordEncoderPort.class);
        TokenProviderPort tok = mock(TokenProviderPort.class);

        when(repo.existsByUsername("mari")).thenReturn(true);

        UsuarioService service = new UsuarioService(repo, enc, tok);

        assertThrows(IllegalStateException.class,
                () -> service.registrar("mari", "123", Set.of(Perfil.ROLE_USER)));
    }

    @Test
    void login_ok_quando_senha_confere() {
        UsuarioRepositoryPort repo = mock(UsuarioRepositoryPort.class);
        PasswordEncoderPort enc = mock(PasswordEncoderPort.class);
        TokenProviderPort tok = mock(TokenProviderPort.class);

        Usuario user = new Usuario(UUID.randomUUID(), "mari", "hash", Set.of(Perfil.ROLE_USER));
        when(repo.buscarPorUsername("mari")).thenReturn(Optional.of(user));
        when(enc.matches("123", "hash")).thenReturn(true);
        when(tok.generateToken(user)).thenReturn("token-xyz");

        UsuarioService service = new UsuarioService(repo, enc, tok);

        String token = service.login("mari", "123");

        assertEquals("token-xyz", token);
    }

    @Test
    void login_rejeita_senha_invalida() {
        UsuarioRepositoryPort repo = mock(UsuarioRepositoryPort.class);
        PasswordEncoderPort enc = mock(PasswordEncoderPort.class);
        TokenProviderPort tok = mock(TokenProviderPort.class);

        Usuario user = new Usuario(UUID.randomUUID(), "mari", "hash", Set.of(Perfil.ROLE_USER));
        when(repo.buscarPorUsername("mari")).thenReturn(Optional.of(user));
        when(enc.matches("errada", "hash")).thenReturn(false);

        UsuarioService service = new UsuarioService(repo, enc, tok);

        assertThrows(IllegalArgumentException.class,
                () -> service.login("mari", "errada"));
    }
}