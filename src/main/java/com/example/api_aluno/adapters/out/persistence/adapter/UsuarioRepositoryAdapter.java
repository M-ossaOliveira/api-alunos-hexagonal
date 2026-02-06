package com.example.api_aluno.adapters.out.persistence.adapter;

import com.example.api_aluno.adapters.out.persistence.entity.UsuarioEntity;
import com.example.api_aluno.adapters.out.persistence.repository.SpringDataUsuarioRepository;
import com.example.api_aluno.domain.usuario.Usuario;
import com.example.api_aluno.mapper.UsuarioMapper;
import com.example.api_aluno.ports.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/*Implementa a PORT UsuarioRepositoryPort
* usando o SpringDataUsuarioRepository e o UsuarioMapper
*
* É o plug que "combina" no encaixe da port de saída
*
* O Service fala com a Port, quem executa de verdade é este adapter
*
* Ele é "chamado" pelo UsuarioService por meio da port*/

@Component
public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {

    private final SpringDataUsuarioRepository repo;

    public UsuarioRepositoryAdapter(SpringDataUsuarioRepository repo) {
        this.repo = repo;
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        UsuarioEntity entity = UsuarioMapper.toEntity(usuario);
        UsuarioEntity saved = repo.save(entity);
        return UsuarioMapper.toDomain(saved);
    }

    @Override
    public Optional<Usuario> buscarPorId(UUID id) {
        return repo.findById(id).map(UsuarioMapper::toDomain);
    }

    @Override
    public Optional<Usuario> buscarPorUsername(String username) {
        return repo.findByUsername(username).map(UsuarioMapper::toDomain);
    }

    @Override
    public boolean existsByUsername(String username) {
        return repo.existsByUsername(username);
    }
}