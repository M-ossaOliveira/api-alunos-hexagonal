package com.example.api_aluno.adapters.out.persistence.entity;

import com.example.api_aluno.domain.usuario.Perfil;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
public class UsuarioEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id; // casa com o domínio
    @Column(name="username", nullable = false, unique = true)
    private String username;
    @Column(name = "senha_hash", nullable = false)
    private String senhaHash;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "usuario_perfis", joinColumns = @JoinColumn(name = "usuario_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "perfil", nullable = false)
    private Set<Perfil> perfis = new HashSet<>();
    public UsuarioEntity() { }
    public UsuarioEntity(UUID id, String username, String senhaHash, Set<Perfil> perfis) {
        this.id = id;
        this.username = username;
        this.senhaHash = senhaHash;
        this.perfis = perfis != null ? perfis : new HashSet<>();
    }
    public UUID getId() { return id; }
    public String getUsername() { return username; }
    public String getSenhaHash() { return senhaHash; }
    public Set<Perfil> getPerfis() { return perfis; }
    public void setId(UUID id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setSenhaHash(String senhaHash) { this.senhaHash = senhaHash; }
    public void setPerfis(Set<Perfil> perfis) { this.perfis = perfis; }
}
