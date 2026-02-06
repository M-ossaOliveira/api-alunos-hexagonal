
package com.example.api_aluno.application;

import com.example.api_aluno.domain.usuario.Perfil;
import com.example.api_aluno.domain.usuario.Usuario;
import com.example.api_aluno.ports.out.PasswordEncoderPort;
import com.example.api_aluno.ports.out.TokenProviderPort;
import com.example.api_aluno.ports.out.UsuarioRepositoryPort;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Test
    void registrar_ok() {
        UsuarioRepositoryPort repo = mock(UsuarioRepositoryPort.class);
        PasswordEncoderPort enc = mock(PasswordEncoderPort.class);
        TokenProviderPort tok = mock(TokenProviderPort.class);

        when(repo.existsByUsername("mari")).thenReturn(false);
        when(enc.encode("123")).thenReturn("hash");
        when(repo.salvar(any())).thenAnswer(inv -> inv.getArgument(0));

        UsuarioService service = new UsuarioService(repo, enc, tok);
        Usuario u = service.registrar("mari", "123", Set.of(Perfil.ROLE_USER));

        assertNotNull(u.getId());
        assertEquals("mari", u.getUsername());
        assertEquals("hash", u.getSenhaHash());
    }

    @Test
    void login_senha_invalida() {
        UsuarioRepositoryPort repo = mock(UsuarioRepositoryPort.class);
        PasswordEncoderPort enc = mock(PasswordEncoderPort.class);
        TokenProviderPort tok = mock(TokenProviderPort.class);

        Usuario user = new Usuario(UUID.randomUUID(), "mari", "hash", Set.of(Perfil.ROLE_USER));
        when(repo.buscarPorUsername("mari")).thenReturn(Optional.of(user));
        when(enc.matches("errada", "hash")).thenReturn(false);

        UsuarioService service = new UsuarioService(repo, enc, tok);
        assertThrows(IllegalArgumentException.class, () -> service.login("mari", "errada"));
    }
}
