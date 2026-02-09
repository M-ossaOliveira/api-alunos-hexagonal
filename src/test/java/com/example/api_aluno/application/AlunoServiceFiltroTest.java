package com.example.api_aluno.application;

import com.example.api_aluno.domain.aluno.Aluno;
import com.example.api_aluno.ports.out.AlunoRepositoryPort;
import com.example.api_aluno.ports.out.TurmaRepositoryPort;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AlunoServiceFiltroTest {
    @Test
    void filtrar_ok(){
        AlunoRepositoryPort aRepo = mock(AlunoRepositoryPort.class);
        TurmaRepositoryPort tRepo = mock(TurmaRepositoryPort.class);
        var pageable = PageRequest.of(0, 10);
        var aluno = new Aluno(UUID.randomUUID(), "Ana", "ana@ex.com", null);
        when(aRepo.buscarPorNomeOuEmail(eq("ana"), eq(""), eq(pageable)))
                .thenReturn(new PageImpl<>(List.of(aluno), pageable, 1));
        var svc = new AlunoService(aRepo, tRepo);
        Page<Aluno> page = svc.filtrar("ana", null, pageable);
        assertEquals(1, page.getTotalElements());
        assertEquals("Ana", page.getContent().get(0).getNome());
    }
}
