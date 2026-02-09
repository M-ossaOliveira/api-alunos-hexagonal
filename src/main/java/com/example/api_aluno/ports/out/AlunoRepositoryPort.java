package com.example.api_aluno.ports.out;

import com.example.api_aluno.domain.aluno.Aluno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.UUID;

public interface AlunoRepositoryPort{
    Aluno salvar(Aluno aluno);
    Optional<Aluno> buscarPorId(UUID id);
    List<Aluno> listar();
    boolean existsByEmail(String email);
    void deleteById(UUID id);
    // Novo: suporte a filtros paginados por nome/email
    Page<Aluno> buscarPorNomeOuEmail(String nome, String email, Pageable pageable);
}
