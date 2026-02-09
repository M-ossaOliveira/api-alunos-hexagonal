package com.example.api_aluno.adapters.in.web.aluno.dto;

import java.util.UUID;

public class AlunoResponse {
    private UUID id;
    private String nome;
    private String email;
    private UUID turmaId;

    public AlunoResponse() {}

    public AlunoResponse(UUID id, String nome, String email, UUID turmaId) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.turmaId = turmaId;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public UUID getTurmaId() { return turmaId; }
    public void setTurmaId(UUID turmaId) { this.turmaId = turmaId; }
}