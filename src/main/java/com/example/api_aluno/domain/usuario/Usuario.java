package com.example.api_aluno.domain.usuario;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

public class Usuario {

    private static final Pattern USERNAME_PATTERN =
            Pattern.compile("^[\\p{L}\\p{M}0-9._-]{3,30}$");

    private final UUID id;             // Identificador de domínio
    private final String username;     // Identidade do usuário
    private final String senhaHash;    // Nunca senha pura no domínio
    private final Set<Perfil> perfis;  // ROLE_USER / ROLE_ADMIN

    public Usuario(UUID id, String username, String senhaHash, Set<Perfil> perfis) {
        if (id == null) {
            throw new IllegalArgumentException("id é obrigatório");
        }
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("username é obrigatório");
        }
        String u = username.trim();
        if (!USERNAME_PATTERN.matcher(u).matches()) {
            throw new IllegalArgumentException("username inválido (3-30; letras, números, ., _, -)");
        }
        if (senhaHash == null || senhaHash.isBlank()) {
            throw new IllegalArgumentException("senhaHash é obrigatório");
        }
        if (perfis == null) {
            throw new IllegalArgumentException("perfis é obrigatório");
        }

        this.id = id;
        this.username = u;
        this.senhaHash = senhaHash;
        this.perfis = Set.copyOf(perfis); // cópia imutável defensiva
    }

    public UUID getId() { return id; }
    public String getUsername() { return username; }
    public String getSenhaHash() { return senhaHash; }
    public Set<Perfil> getPerfis() { return perfis; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario that = (Usuario) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}