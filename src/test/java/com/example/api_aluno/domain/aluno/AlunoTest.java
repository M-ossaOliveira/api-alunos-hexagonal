package com.example.api_aluno.domain.aluno;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AlunoTest {

    @Test
    void criaAlunoValido() {
        UUID id = UUID.randomUUID();
        Aluno aluno = new Aluno(id, "Ana Maria", "ana@ex.com", null);

        assertEquals(id, aluno.getId());
        assertEquals("Ana Maria", aluno.getNome());
        assertEquals("ana@ex.com", aluno.getEmail());
        assertNull(aluno.getTurmaId());
    }

    @Test
    void rejeitaIdNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Aluno(null, "Ana", "ana@ex.com", null));
    }

    @Test
    void rejeitaNomeNuloOuBranco() {
        UUID id = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class,
                () -> new Aluno(id, null, "ana@ex.com", null));

        assertThrows(IllegalArgumentException.class,
                () -> new Aluno(id, "   ", "ana@ex.com", null));
    }

    @Test
    void rejeitaEmailNuloOuBranco() {
        UUID id = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class,
                () -> new Aluno(id, "Ana", null, null));

        assertThrows(IllegalArgumentException.class,
                () -> new Aluno(id, "Ana", "   ", null));
    }
}