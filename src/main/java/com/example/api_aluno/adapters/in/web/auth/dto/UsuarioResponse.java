package com.example.api_aluno.adapters.in.web.auth.dto;

import com.example.api_aluno.domain.usuario.Perfil;

import java.util.Set;
import java.util.UUID;

public class UsuarioResponse {
    private UUID id;
    private String username;
    private Set<Perfil> perfis;

    public UsuarioResponse() {}
    public UsuarioResponse(UUID id, String username, Set<Perfil> perfis) {
        this.id = id;
        this.username = username;
        this.perfis = perfis;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Set<Perfil> getPerfis() { return perfis; }
    public void setPerfis(Set<Perfil> perfis) { this.perfis = perfis; }
}
