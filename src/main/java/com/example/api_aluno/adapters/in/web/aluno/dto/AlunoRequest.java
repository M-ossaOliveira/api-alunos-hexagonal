package com.example.api_aluno.adapters.in.web.aluno.dto;
<<<<<<< HEAD
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
=======

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
>>>>>>> 00bc3f9 (Commit 1 - corrigindo erro não mapeava MethodArgumentNotValidException. Agora estável.)
import java.util.UUID;

public class AlunoRequest {

    @NotBlank
<<<<<<< HEAD
    @Pattern(
            regexp = "^[\\p{L} ]{3,30}$",
            message = "nome deve conter 3-30 letras (pode ter espaço e acentos)"
    )
    private String nome;

    @NotBlank
    @Email(message="formato do e-mail inválido", regexp="^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String email;

    private UUID turmaId; // opcional
=======
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
>>>>>>> 00bc3f9 (Commit 1 - corrigindo erro não mapeava MethodArgumentNotValidException. Agora estável.)

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public UUID getTurmaId() { return turmaId; }
    public void setTurmaId(UUID turmaId) { this.turmaId = turmaId; }
}
