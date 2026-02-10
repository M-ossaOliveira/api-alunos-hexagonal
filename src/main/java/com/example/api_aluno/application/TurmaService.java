package com.example.api_aluno.application;

import com.example.api_aluno.domain.aluno.Aluno;
import com.example.api_aluno.domain.turma.Turma;
import com.example.api_aluno.ports.in.TurmaUseCases;
import com.example.api_aluno.ports.out.AlunoRepositoryPort;
import com.example.api_aluno.ports.out.TurmaRepositoryPort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Implementação dos casos de uso de Turma */
public class TurmaService implements TurmaUseCases {
    private final TurmaRepositoryPort turmaRepository;
    private final AlunoRepositoryPort alunoRepository; // novo

    public TurmaService(TurmaRepositoryPort turmaRepository, AlunoRepositoryPort alunoRepository) {
        this.turmaRepository = turmaRepository;
        this.alunoRepository = alunoRepository;
    }

    @Override
    public Turma criar(String nome, Integer anoLetivo) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("nome é obrigatório");
        }
        if (anoLetivo == null) {
            throw new IllegalArgumentException("anoLetivo é obrigatório");
        }
        if (turmaRepository.existsByNomeAndAnoLetivo(nome, anoLetivo)) {
            throw new IllegalStateException("turma já existente para o ano letivo");
        }
        Turma nova = new Turma(UUID.randomUUID(), nome, anoLetivo);
        return turmaRepository.salvar(nova);
    }

    @Override
    public Optional<Turma> buscarPorId(UUID id) {
        return turmaRepository.buscarPorId(id);
    }

    @Override
    public List<Turma> listar() {
        return turmaRepository.listar();
    }

    @Override
    public Turma atualizar(UUID id, String nome, Integer anoLetivo) {
        if (id == null) throw new IllegalArgumentException("id é obrigatório");
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("nome é obrigatório");
        if (anoLetivo == null) throw new IllegalArgumentException("anoLetivo é obrigatório");
        Turma atualizada = new Turma(id, nome, anoLetivo);
        return turmaRepository.salvar(atualizada);
    }

    @Override
    public void excluir(UUID id) {
        if (id == null) throw new IllegalArgumentException("id é obrigatório");

        // 1) Desvincular todos os alunos da turma (turma_id = NULL)
        var alunos = alunoRepository.buscarPorTurmaId(id);
        for (var a : alunos) {
            var desvinculado = new Aluno(a.getId(), a.getNome(), a.getEmail(), null);
            alunoRepository.salvar(desvinculado);
        }

        // 2) Excluir a turma após desvincular
        turmaRepository.deleteById(id);
    }
}
