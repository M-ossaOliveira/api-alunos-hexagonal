package com.example.api_aluno.application;

import com.example.api_aluno.domain.aluno.Aluno;
import com.example.api_aluno.ports.in.AlunoUseCases;
import com.example.api_aluno.ports.out.AlunoRepositoryPort;
import com.example.api_aluno.ports.out.TurmaRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Implementação dos casos de uso de Aluno */
public class AlunoService implements AlunoUseCases {
    private final AlunoRepositoryPort alunoRepository;
    private final TurmaRepositoryPort turmaRepository;

    public AlunoService(AlunoRepositoryPort alunoRepository, TurmaRepositoryPort turmaRepository) {
        this.alunoRepository = alunoRepository;
        this.turmaRepository = turmaRepository;
    }

    @Override
    public Aluno cadastrar(String nome, String email, UUID turmaId) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("nome é obrigatório");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email é obrigatório");
        }
        if (alunoRepository.existsByEmail(email)) {
            throw new IllegalStateException("email já está em uso");
        }
        if (turmaId != null && turmaRepository.buscarPorId(turmaId).isEmpty()) {
            throw new IllegalArgumentException("turma inexistente");
        }
        Aluno novo = new Aluno(UUID.randomUUID(), nome, email, turmaId);
        return alunoRepository.salvar(novo);
    }

    @Override
    public Optional<Aluno> buscarPorId(UUID id) {
        return alunoRepository.buscarPorId(id);
    }

    @Override
    public List<Aluno> listar() {
        return alunoRepository.listar();
    }

    @Override
    public Aluno atualizar(UUID id, String nome, String email, UUID turmaId) {
        if (id == null) throw new IllegalArgumentException("id é obrigatório");
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("nome é obrigatório");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("email é obrigatório");
        if (turmaId != null && turmaRepository.buscarPorId(turmaId).isEmpty()) {
            throw new IllegalArgumentException("turma inexistente");
        }
        Aluno atualizado = new Aluno(id, nome, email, turmaId);
        return alunoRepository.salvar(atualizado);
    }

    @Override
    public void excluir(UUID id) {
        if (id == null) throw new IllegalArgumentException("id é obrigatório");
        alunoRepository.deleteById(id);
    }

    @Override
    public Page<Aluno> filtrar(String nome, String email, Pageable pageable) {
        return alunoRepository.buscarPorNomeOuEmail(
                nome == null ? "" : nome.trim(),
                email == null ? "" : email.trim(),
                pageable
                                                   );
    }
}
