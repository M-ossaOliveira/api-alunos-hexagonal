package com.example.api_aluno.adapters.out.persistence.adapter;

import com.example.api_aluno.adapters.out.persistence.entity.AlunoEntity;
import com.example.api_aluno.adapters.out.persistence.repository.SpringDataAlunoRepository;
import com.example.api_aluno.domain.aluno.Aluno;
import com.example.api_aluno.mapper.AlunoEntityMapper;
import com.example.api_aluno.ports.out.AlunoRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.UUID;

@Component
public class AlunoRepositoryAdapter implements AlunoRepositoryPort {
    private final SpringDataAlunoRepository repo;
    private final AlunoEntityMapper mapper;

    public AlunoRepositoryAdapter(SpringDataAlunoRepository r, AlunoEntityMapper m){
        this.repo=r;this.mapper=m;
    }

    @Override
    public Aluno salvar(Aluno a){
        AlunoEntity e=mapper.toEntity(a);
        return mapper.toDomain(repo.save(e));
    }

    @Override
    public Optional<Aluno> buscarPorId(UUID id){
        return repo.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Aluno> listar(){
        return repo.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public boolean existsByEmail(String email){
        return repo.existsByEmail(email);
    }

    @Override
    public void deleteById(UUID id){
        repo.deleteById(id);
    }

    @Override
    public Page<Aluno> buscarPorNomeOuEmail(String nome, String email, Pageable pageable){
        var page = repo.findByNomeContainingIgnoreCaseOrEmailContainingIgnoreCase(
                nome == null ? "" : nome,
                email == null ? "" : email,
                pageable
                                                                                 );
        return page.map(mapper::toDomain);
    }
}
