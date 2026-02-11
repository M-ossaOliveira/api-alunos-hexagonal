package com.example.api_aluno.application;

import com.example.api_aluno.domain.turma.Turma;
import com.example.api_aluno.ports.out.TurmaRepositoryPort;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TurmaServiceUnitTest {

    @Test
    void criaTurmaQuandoNaoDuplicada() {
        TurmaRepositoryPort repo = mock(TurmaRepositoryPort.class);

        // antes: "1A" (2 chars) → agora use 3+ chars:
        when(repo.existsByNomeAndAnoLetivo("1A1", 2026)).thenReturn(false);
        when(repo.salvar(any())).thenAnswer(inv -> inv.getArgument(0));

        TurmaService service = new TurmaService(repo);

        Turma t = service.criar("1A1", 2026);

        assertNotNull(t.getId());
        assertEquals("1A1", t.getNome());
        assertEquals(2026, t.getAnoLetivo());
    }

    @Test
    void rejeitaTurmaDuplicada() {
        TurmaRepositoryPort repo = mock(TurmaRepositoryPort.class);

        when(repo.existsByNomeAndAnoLetivo("1A", 2026)).thenReturn(true);

        TurmaService service = new TurmaService(repo);

        assertThrows(IllegalStateException.class,
                () -> service.criar("1A", 2026));
    }
}