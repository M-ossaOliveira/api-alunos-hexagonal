package com.example.api_aluno.adapters.in.web.aluno.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public class AlunoRequest {
    @NotBlank
    private String nome;
    @NotBlank
    @Email
    private String email;
    private UUID turmaId; // opcional

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public UUID getTurmaId() { return turmaId; }
    public void setTurmaId(UUID turmaId) { this.turmaId = turmaId; }
}