package com.example.api_aluno.adapters.in.web.auth.dto;

import com.example.api_aluno.domain.usuario.Perfil;
import java.util.Set;

public class RegisterRequest {
    private String username;
    private String password;
    private Set<Perfil> perfis;
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Set<Perfil> getPerfis() { return perfis; }
    public void setPerfis(Set<Perfil> perfis) { this.perfis = perfis; }
}
