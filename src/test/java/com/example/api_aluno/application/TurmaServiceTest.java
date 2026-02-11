package com.example.api_aluno.application;

import com.example.api_aluno.domain.turma.Turma;
import com.example.api_aluno.ports.out.TurmaRepositoryPort;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TurmaServiceTest {

    @Test
    void criar_ok() {
        TurmaRepositoryPort repo = mock(TurmaRepositoryPort.class);
        when(repo.existsByNomeAndAnoLetivo("1A1", 2026)).thenReturn(false); // <- 3+ chars
        when(repo.salvar(any())).thenAnswer(inv -> inv.getArgument(0));

        TurmaService service = new TurmaService(repo);

        Turma turma = service.criar("1A1", 2026); // <- 3+ chars

        assertNotNull(turma.getId());
        assertEquals("1A1", turma.getNome());
        assertEquals(2026, turma.getAnoLetivo());
    }

    @Test
    void criar_duplicada() {
        TurmaRepositoryPort repo = mock(TurmaRepositoryPort.class);
        when(repo.existsByNomeAndAnoLetivo("1A1", 2026)).thenReturn(true); // <- manter consistente

        TurmaService service = new TurmaService(repo);

        assertThrows(IllegalStateException.class, () -> service.criar("1A1", 2026));
    }

    @Test
    void atualizar_ok() {
        TurmaRepositoryPort repo = mock(TurmaRepositoryPort.class);
        when(repo.salvar(any())).thenAnswer(inv -> inv.getArgument(0));

        TurmaService service = new TurmaService(repo);

        UUID id = UUID.randomUUID();
        Turma turma = service.atualizar(id, "2B2", 2025); // <- 3+ chars

        assertEquals(id, turma.getId());
        assertEquals("2B2", turma.getNome());
        assertEquals(2025, turma.getAnoLetivo());
    }

    @Test
    void excluir_ok() {
        TurmaRepositoryPort repo = mock(TurmaRepositoryPort.class);

        TurmaService service = new TurmaService(repo);

        UUID id = UUID.randomUUID();
        service.excluir(id);

        verify(repo, times(1)).deleteById(id);
    }
}
