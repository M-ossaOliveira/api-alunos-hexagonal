package com.example.api_aluno.ports.out;

import com.example.api_aluno.domain.turma.Turma;
import java.util.*;
import java.util.UUID;

public interface TurmaRepositoryPort{
    Turma salvar(Turma turma);
    Optional<Turma> buscarPorId(UUID id);
    List<Turma> listar();
    boolean existsByNomeAndAnoLetivo(String nome,Integer anoLetivo);
    void deleteById(UUID id);
}
