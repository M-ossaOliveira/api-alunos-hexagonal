
package com.example.api_aluno.adapters.out.persistence.repository;

import com.example.api_aluno.adapters.out.persistence.entity.AlunoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import java.util.UUID;

public interface SpringDataAlunoRepository extends JpaRepository<AlunoEntity,UUID>{
    Optional<AlunoEntity> findByEmail(String email);
    boolean existsByEmail(String email);

    Page<AlunoEntity> findByNomeContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String nome, String email, Pageable pageable);
}
