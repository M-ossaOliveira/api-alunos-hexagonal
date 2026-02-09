package com.example.api_aluno.application;
import com.example.api_aluno.domain.aluno.Aluno;
import com.example.api_aluno.ports.out.*;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlunoServiceTest{
    @Test void cadastrar_ok(){
        AlunoRepositoryPort aRepo=mock(AlunoRepositoryPort.class);
        TurmaRepositoryPort tRepo=mock(TurmaRepositoryPort.class);
        when(aRepo.existsByEmail("a@b.com")).thenReturn(false);
        when(tRepo.buscarPorId(any())).thenReturn(Optional.ofNullable(null));
        when(aRepo.salvar(any())).thenAnswer(inv->inv.getArgument(0));
        AlunoService svc=new AlunoService(aRepo,tRepo); Aluno a=svc.cadastrar("Ana","a@b.com",null);
        assertNotNull(a.getId()); assertEquals("Ana",a.getNome()); assertEquals("a@b.com",a.getEmail());
    }
    @Test void cadastrar_email_duplicado(){
        AlunoRepositoryPort aRepo=mock(AlunoRepositoryPort.class);
        TurmaRepositoryPort tRepo=mock(TurmaRepositoryPort.class);
        when(aRepo.existsByEmail("a@b.com")).thenReturn(true);
        AlunoService svc=new AlunoService(aRepo,tRepo);
        assertThrows(IllegalStateException.class,()->svc.cadastrar("Ana","a@b.com",null));
    }
}
