package com.example.api_aluno.application;

import com.example.api_aluno.domain.aluno.Aluno;
import com.example.api_aluno.domain.turma.Turma;
import com.example.api_aluno.ports.out.AlunoRepositoryPort;
import com.example.api_aluno.ports.out.TurmaRepositoryPort;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlunoServiceMoreUnitTest {

    @Test
    void cadastrar_ok_sem_turma() {
        AlunoRepositoryPort aRepo = mock(AlunoRepositoryPort.class);
        TurmaRepositoryPort tRepo = mock(TurmaRepositoryPort.class);

        when(aRepo.existsByEmail("ana@ex.com")).thenReturn(false);
        when(aRepo.salvar(any())).thenAnswer(inv -> inv.getArgument(0));

        AlunoService svc = new AlunoService(aRepo, tRepo);

        Aluno a = svc.cadastrar("Ana", "ana@ex.com", null);

        assertNotNull(a.getId());
        assertEquals("Ana", a.getNome());
        assertEquals("ana@ex.com", a.getEmail());
        assertNull(a.getTurmaId());
    }

    @Test
    void cadastrar_ok_com_turma_existente() {
        AlunoRepositoryPort aRepo = mock(AlunoRepositoryPort.class);
        TurmaRepositoryPort tRepo = mock(TurmaRepositoryPort.class);

        UUID turmaId = UUID.randomUUID();
        // Turma de domínio com nome >= 3 chars (caso você tenha fortalecido a entidade)
        when(tRepo.buscarPorId(turmaId)).thenReturn(Optional.of(new Turma(turmaId, "Turma 1", 2026)));
        when(aRepo.existsByEmail("ana@ex.com")).thenReturn(false);
        when(aRepo.salvar(any())).thenAnswer(inv -> inv.getArgument(0));

        AlunoService svc = new AlunoService(aRepo, tRepo);

        Aluno a = svc.cadastrar("Ana", "ana@ex.com", turmaId);

        assertNotNull(a.getId());
        assertEquals(turmaId, a.getTurmaId());
    }

    @Test
    void cadastrar_rejeita_email_duplicado() {
        AlunoRepositoryPort aRepo = mock(AlunoRepositoryPort.class);
        TurmaRepositoryPort tRepo = mock(TurmaRepositoryPort.class);

        when(aRepo.existsByEmail("ana@ex.com")).thenReturn(true);

        AlunoService svc = new AlunoService(aRepo, tRepo);

        assertThrows(IllegalStateException.class,
                () -> svc.cadastrar("Ana", "ana@ex.com", null));
    }

    @Test
    void cadastrar_rejeita_turma_inexistente_quando_turmaId_presente() {
        AlunoRepositoryPort aRepo = mock(AlunoRepositoryPort.class);
        TurmaRepositoryPort tRepo = mock(TurmaRepositoryPort.class);

        when(aRepo.existsByEmail("ana@ex.com")).thenReturn(false);

        UUID turmaId = UUID.randomUUID();
        when(tRepo.buscarPorId(turmaId)).thenReturn(Optional.empty());

        AlunoService svc = new AlunoService(aRepo, tRepo);

        assertThrows(IllegalArgumentException.class,
                () -> svc.cadastrar("Ana", "ana@ex.com", turmaId));
    }

    @Test
    void atualizar_ok() {
        AlunoRepositoryPort aRepo = mock(AlunoRepositoryPort.class);
        TurmaRepositoryPort tRepo = mock(TurmaRepositoryPort.class);

        when(aRepo.salvar(any())).thenAnswer(inv -> inv.getArgument(0));

        AlunoService svc = new AlunoService(aRepo, tRepo);

        UUID id = UUID.randomUUID();
        UUID turmaId = null; // pode ser null ou id válido
        Aluno a = svc.atualizar(id, "Ana Paula", "ana@ex.com", turmaId);

        assertEquals(id, a.getId());
        assertEquals("Ana Paula", a.getNome());
        assertEquals("ana@ex.com", a.getEmail());
        assertNull(a.getTurmaId());
    }

    @Test
    void excluir_ok() {
        AlunoRepositoryPort aRepo = mock(AlunoRepositoryPort.class);
        TurmaRepositoryPort tRepo = mock(TurmaRepositoryPort.class);

        AlunoService svc = new AlunoService(aRepo, tRepo);

        UUID id = UUID.randomUUID();
        svc.excluir(id);

        verify(aRepo, times(1)).deleteById(id);
    }
}