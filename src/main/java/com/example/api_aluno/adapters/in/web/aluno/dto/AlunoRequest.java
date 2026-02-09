package com.example.api_aluno.adapters.in.web.auth.dto;
import jakarta.validation.constraints.*;
import java.util.UUID;
public class AlunoRequest {
    @NotBlank
    private String nome;
    @NotBlank @Email
    private String email;
    private UUID turmaId;

    public String getNome(){return nome;}
    public void setNome(String n){this.nome=n;}
    public String getEmail(){return email;}
    public void setEmail(String e){this.email=e;}
    public UUID getTurmaId(){return turmaId;}
    public void setTurmaId(UUID t){this.turmaId=t;} }