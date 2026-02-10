package com.example.api_aluno.adapters.in.web.aluno.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.UUID;

public class AlunoRequest {
    @NotBlank
    @Pattern(
            regexp = "^[\\p{L} ]{3,30}$",
            message = "nome deve conter 3-30 letras (pode ter espaço e acentos)"
    )
    private String nome;

    @NotBlank
    @Email(message="formato do e-mail inválido", regexp="^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String email;

    private UUID turmaId; // opcional

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public UUID getTurmaId() { return turmaId; }
    public void setTurmaId(UUID turmaId) { this.turmaId = turmaId; }
}
