package com.example.api_aluno.adapters.in.web.auth.dto;
import java.util.UUID;


public class AlunoResponse
{
    private UUID id;
    private String nome;
    private String email;
    private UUID turmaId;

    public AlunoResponse(){}
    public AlunoResponse(UUID id,String n,String e,UUID t){
        this.id=id;this.nome=n;this.email=e;this.turmaId=t;
    }

    public UUID getId(){return id;}
    public void setId(UUID id){this.id=id;}

    public String getNome(){return nome;}
    public void setNome(String n){this.nome=n;}

    public String getEmail(){return email;}
    public void setEmail(String e){this.email=e;}

    public UUID getTurmaId(){return turmaId;}
    public void setTurmaId(UUID t){this.turmaId=t;}
}

