package com.example.api_aluno.domain.turma;

import java.util.Objects;
import java.util.UUID;

public class Turma {
    private final UUID id;
    private final String nome;
    private final Integer anoLetivo;
    public Turma(UUID id, String nome, Integer anoLetivo) {
        this.id = id;
        this.nome = nome;
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
    @Override public int hashCode() { return Objects.hash(id); }
}
