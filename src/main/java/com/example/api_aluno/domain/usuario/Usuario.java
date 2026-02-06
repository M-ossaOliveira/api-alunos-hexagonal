package com.example.api_aluno.domain.usuario;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Usuario {
    private final UUID id;            // Identificador de domínio
    private final String username;    // Identidade do usuário
    private final String senhaHash;   // Nunca senha pura no domínio
    private final Set<Perfil> perfis; // ROLE_USER / ROLE_ADMIN

    public Usuario(UUID id, String username, String senhaHash, Set<Perfil> perfis) {
        this.id = id;
        this.username = username;
        this.senhaHash = senhaHash;
        this.perfis = perfis;
    }

    public UUID getId() { return id; }
    public String getUsername() { return username; }
    public String getSenhaHash() { return senhaHash; }
    public Set<Perfil> getPerfis() { return perfis; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario that = (Usuario) o;
        return Objects.equals(id, that.id);
    }
    @Override public int hashCode() { return Objects.hash(id); }
}