package com.example.api_aluno.domain.turma;

import java.util.Objects;
import java.util.UUID;

public class Turma {

    private final UUID id;
    private final String nome;
    private final Integer anoLetivo;

    public Turma(UUID id, String nome, Integer anoLetivo) {

        if (id == null)
            throw new IllegalArgumentException("id é obrigatório");

        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("nome é obrigatório");

        if (nome.trim().length() < 3)
            throw new IllegalArgumentException("nome deve ter ao menos 3 caracteres");

        if (anoLetivo == null)
            throw new IllegalArgumentException("anoLetivo é obrigatório");

        if (anoLetivo < 1900 || anoLetivo > 2100)
            throw new IllegalArgumentException("anoLetivo fora da faixa aceitável");

        this.id = id;
        this.nome = nome.trim();
        this.anoLetivo = anoLetivo;
    }

    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public Integer getAnoLetivo() { return anoLetivo; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Turma)) return false;
        Turma turma = (Turma) o;
        return Objects.equals(id, turma.id);
    }

    @Override public int hashCode() {
        return Objects.hash(id);
    }
}