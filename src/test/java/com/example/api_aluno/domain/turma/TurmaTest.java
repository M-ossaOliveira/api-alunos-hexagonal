package com.example.api_aluno.domain.turma;

import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TurmaTest {

    @Test
    void criaTurmaValida() {
        UUID id = UUID.randomUUID();

        Turma t = new Turma(id, "Algoritmos I", 2026);

        assertEquals(id, t.getId());
        assertEquals("Algoritmos I", t.getNome());
        assertEquals(2026, t.getAnoLetivo());
    }

    @Test
    void rejeitaNomeNuloOuEmBranco() {
        UUID id = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class,
                () -> new Turma(id, null, 2026));

        assertThrows(IllegalArgumentException.class,
                () -> new Turma(id, "   ", 2026));
    }

    @Test
    void rejeitaAnoLetivoNulo() {
        UUID id = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class,
                () -> new Turma(id, "Algoritmos", null));
    }
}