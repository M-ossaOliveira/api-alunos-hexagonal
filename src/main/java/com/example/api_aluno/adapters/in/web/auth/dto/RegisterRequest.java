package com.example.api_aluno.adapters.in.web.auth.dto;

import com.example.api_aluno.domain.usuario.Perfil;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.util.Set;

public class RegisterRequest {

    @NotBlank(message = "username é obrigatório")
    @Schema(
            description = "Username",
            example = "ana.silva"
    )
    @Pattern(
            regexp = "^[\\p{L}\\p{M}0-9._-]{3,30}$",
            message = "username deve conter 3-30 caracteres (letras, números, ., _, -)"
    )
    private String username;

    @NotBlank(message = "password é obrigatório")
    @Size(min = 6, message = "password deve ter ao menos 6 caracteres")
    private String password;

    private Set<Perfil> perfis;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Set<Perfil> getPerfis() { return perfis; }
    public void setPerfis(Set<Perfil> perfis) { this.perfis = perfis; }
}