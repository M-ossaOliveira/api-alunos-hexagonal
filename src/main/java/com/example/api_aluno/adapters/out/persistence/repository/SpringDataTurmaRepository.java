package com.example.api_aluno.adapters.out.persistence.repository;

import com.example.api_aluno.adapters.out.persistence.entity.TurmaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataTurmaRepository extends JpaRepository<TurmaEntity,java.util.UUID>
{
    boolean existsByNomeAndAnoLetivo(String nome,Integer anoLetivo);
}
