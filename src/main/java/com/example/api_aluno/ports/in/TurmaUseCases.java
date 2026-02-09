package com.example.api_aluno.ports.in;

import com.example.api_aluno.domain.turma.Turma;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TurmaUseCases {
    Turma criar(String nome, Integer anoLetivo);
    Optional<Turma> buscarPorId(UUID id);
    List<Turma> listar();
    Turma atualizar(UUID id, String nome, Integer anoLetivo);
    void excluir(UUID id);
}
