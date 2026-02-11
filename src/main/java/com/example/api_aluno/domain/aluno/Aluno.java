package com.example.api_aluno.domain.aluno;

import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class Aluno {

    private final UUID id;
    private final String nome;
    private final String email;
    private final UUID turmaId; // associação por ID pode ser nula

    public Aluno(UUID id, String nome, String email, UUID turmaId) {
        if (id == null) {
            throw new IllegalArgumentException("id é obrigatório");
        }
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("nome é obrigatório");
        }
        String nomeNorm = nome.trim();
        if (nomeNorm.length() < 3 || nomeNorm.length() > 60) {
            throw new IllegalArgumentException("nome deve ter entre 3 e 60 caracteres");
        }

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email é obrigatório");
        }
        String emailNorm = email.trim().toLowerCase(Locale.ROOT);
        if (!isEmailMinimamenteValido(emailNorm)) {
            throw new IllegalArgumentException("email inválido");
        }

        this.id = id;
        this.nome = nomeNorm;
        this.email = emailNorm;
        this.turmaId = turmaId; // pode ser null
    }

    private static boolean isEmailMinimamenteValido(String e) {
        int at = e.indexOf('@');
        if (at <= 0 || at == e.length() - 1) return false;
        String domain = e.substring(at + 1);
        return domain.contains("."); // checagem simples e suficiente para domínio puro
    }

    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public UUID getTurmaId() { return turmaId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Aluno)) return false;
        Aluno that = (Aluno) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}