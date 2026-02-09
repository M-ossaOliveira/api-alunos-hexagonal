package com.example.api_aluno.domain.aluno;

import java.util.Objects;
import java.util.UUID;

/** Domínio puro (placeholder) */
public class Aluno {
    private final UUID id;
    private final String nome;
    private final String email;
    private final UUID turmaId; // associação por ID, para manter domínio puro

    public Aluno(UUID id, String nome, String email, UUID turmaId) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.turmaId = turmaId;
    }

    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public UUID getTurmaId() { return turmaId; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Aluno)) return false;
        Aluno that = (Aluno) o;
        return Objects.equals(id, that.id);
    }
    @Override public int hashCode() { return Objects.hash(id); }
}
