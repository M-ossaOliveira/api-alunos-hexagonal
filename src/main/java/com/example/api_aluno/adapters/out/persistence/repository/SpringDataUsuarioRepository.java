package com.example.api_aluno.adapters.out.persistence.repository;

import com.example.api_aluno.adapters.out.persistence.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/*Interface que faz extensão entre JpaRepository e o UsuarioEntity*/
/*Fornece operações CRUD prontas e queries de convenção*/
/*É chamado pelo UsuarioRepositoryAdapter*/

public interface SpringDataUsuarioRepository extends JpaRepository<UsuarioEntity, UUID> {
    Optional<UsuarioEntity> findByUsername(String username);
    boolean existsByUsername(String username);
}