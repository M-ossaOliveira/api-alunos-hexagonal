
package com.example.api_aluno.adapters.out.persistence.adapter;

import com.example.api_aluno.adapters.out.persistence.entity.TurmaEntity;
import com.example.api_aluno.adapters.out.persistence.repository.SpringDataTurmaRepository;
import com.example.api_aluno.domain.turma.Turma;
import com.example.api_aluno.mapper.TurmaEntityMapper;
import com.example.api_aluno.ports.out.TurmaRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TurmaRepositoryAdapter implements TurmaRepositoryPort {
    private final SpringDataTurmaRepository repo;
    private final TurmaEntityMapper mapper;

    public TurmaRepositoryAdapter(SpringDataTurmaRepository repo, TurmaEntityMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public Turma salvar(Turma turma) {
        TurmaEntity e = mapper.toEntity(turma);
        return mapper.toDomain(repo.save(e));
    }

    @Override
    public Optional<Turma> buscarPorId(UUID id) {
        return repo.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Turma> listar() {
        return repo.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public boolean existsByNomeAndAnoLetivo(String nome, Integer anoLetivo) {
        return repo.existsByNomeAndAnoLetivo(nome, anoLetivo);
    }

    @Override
    public void deleteById(UUID id) {
        repo.deleteById(id);
    }
}
