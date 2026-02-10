package com.example.api_aluno.application;

import com.example.api_aluno.domain.aluno.Aluno;
import com.example.api_aluno.ports.out.AlunoRepositoryPort;
import com.example.api_aluno.ports.out.TurmaRepositoryPort;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TurmaServiceTest {
    @Test
    void criar_ok() {
        TurmaRepositoryPort repo = mock(TurmaRepositoryPort.class);
        AlunoRepositoryPort alunoRepo = mock(AlunoRepositoryPort.class);
        when(repo.existsByNomeAndAnoLetivo("1A", 2026)).thenReturn(false);
        when(repo.salvar(any())).thenAnswer(inv -> inv.getArgument(0));
        TurmaService service = new TurmaService(repo, alunoRepo);
        var turma = service.criar("1A", 2026);
        assertNotNull(turma.getId());
        assertEquals("1A", turma.getNome());
        assertEquals(2026, turma.getAnoLetivo());
    }

    @Test
    void criar_duplicada() {
        TurmaRepositoryPort repo = mock(TurmaRepositoryPort.class);
        AlunoRepositoryPort alunoRepo = mock(AlunoRepositoryPort.class);
        when(repo.existsByNomeAndAnoLetivo("1A", 2026)).thenReturn(true);
        TurmaService service = new TurmaService(repo, alunoRepo);
        assertThrows(IllegalStateException.class, () -> service.criar("1A", 2026));
    }

    @Test
    void atualizar_ok() {
        TurmaRepositoryPort repo = mock(TurmaRepositoryPort.class);
        AlunoRepositoryPort alunoRepo = mock(AlunoRepositoryPort.class);
        when(repo.salvar(any())).thenAnswer(inv -> inv.getArgument(0));
        TurmaService service = new TurmaService(repo, alunoRepo);
        UUID id = UUID.randomUUID();
        var turma = service.atualizar(id, "2B", 2025);
        assertEquals(id, turma.getId());
        assertEquals("2B", turma.getNome());
        assertEquals(2025, turma.getAnoLetivo());
    }

    @Test
    void excluir_ok_desvincula_alunos_e_deleta_turma() {
        TurmaRepositoryPort repo = mock(TurmaRepositoryPort.class);
        AlunoRepositoryPort alunoRepo = mock(AlunoRepositoryPort.class);
        TurmaService service = new TurmaService(repo, alunoRepo);

        UUID turmaId = UUID.randomUUID();
        var aluno = new Aluno(UUID.randomUUID(), "Ana", "ana@ex.com", turmaId);

        when(alunoRepo.buscarPorTurmaId(turmaId)).thenReturn(List.of(aluno));
        when(alunoRepo.salvar(any())).thenAnswer(inv -> inv.getArgument(0));

        service.excluir(turmaId);

        // Verifica desvinculação (turmaId -> null) e exclusão da turma
        verify(alunoRepo, times(1)).buscarPorTurmaId(turmaId);
        verify(alunoRepo, times(1)).salvar(any(Aluno.class));
        verify(repo, times(1)).deleteById(turmaId);
    }
}
