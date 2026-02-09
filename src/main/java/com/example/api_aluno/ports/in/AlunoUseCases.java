package com.example.api_aluno.ports.in;

import com.example.api_aluno.domain.aluno.Aluno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.UUID;

public interface AlunoUseCases {
    Aluno cadastrar(String nome, String email, UUID turmaId);
    Optional<Aluno> buscarPorId(UUID id);
    List<Aluno> listar();
    Aluno atualizar(UUID id, String nome, String email, UUID turmaId);
    void excluir(UUID id);
    // Filtro paginado
    Page<Aluno> filtrar(String nome, String email, Pageable pageable);
    // NOVO: todos os alunos de uma turma
    List<Aluno> listarPorTurma(UUID turmaId);
}
