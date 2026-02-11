package com.example.api_aluno.adapters.in.web.aluno.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.util.UUID;

public class AlunoRequest {

    @NotBlank
    @Schema(
            description = "Nome do Aluno",
            example = "Ana Maria da Silva"
    )
    @Size(min = 3, max = 60, message = "nome deve ter entre 3 e 60 caracteres")
    private String nome;

    @NotBlank
    @Email(message = "formato do e-mail inválido")
    private String email;

    // opcional
    private UUID turmaId;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public UUID getTurmaId() { return turmaId; }
    public void setTurmaId(UUID turmaId) { this.turmaId = turmaId; }
}