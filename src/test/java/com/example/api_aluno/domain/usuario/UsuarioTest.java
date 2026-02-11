package com.example.api_aluno.domain.usuario;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void criaUsuarioValido() {
        UUID id = UUID.randomUUID();

        Usuario u = new Usuario(
                id,
                "mari",
                "hash123",
                Set.of(Perfil.ROLE_USER)
        );

        assertEquals(id, u.getId());
        assertEquals("mari", u.getUsername());
        assertEquals("hash123", u.getSenhaHash());
        assertTrue(u.getPerfis().contains(Perfil.ROLE_USER));
    }

    @Test
    void rejeitaIdNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Usuario(null, "mari", "hash", Set.of(Perfil.ROLE_USER)));
    }

    @Test
    void rejeitaUsernameNuloOuBranco() {
        UUID id = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class,
                () -> new Usuario(id, null, "hash", Set.of(Perfil.ROLE_USER)));

        assertThrows(IllegalArgumentException.class,
                () -> new Usuario(id, "   ", "hash", Set.of(Perfil.ROLE_USER)));
    }

    @Test
    void rejeitaSenhaHashNulaOuBranca() {
        UUID id = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class,
                () -> new Usuario(id, "mari", null, Set.of(Perfil.ROLE_USER)));

        assertThrows(IllegalArgumentException.class,
                () -> new Usuario(id, "mari", "   ", Set.of(Perfil.ROLE_USER)));
    }

    @Test
    void rejeitaPerfisNulos() {
        UUID id = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class,
                () -> new Usuario(id, "mari", "hash", null));
    }
}