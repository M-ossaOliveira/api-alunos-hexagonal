package com.example.api_aluno.adapters.in.web.turma.dto;

import java.util.UUID;

public class TurmaResponse{
    UUID id;
    private String nome;
    private Integer anoLetivo;
    public TurmaResponse(){}
    public TurmaResponse(UUID id,String n,Integer a)
    {
        this.id=id;this.nome=n;this.anoLetivo=a;
    }
    public UUID getId(){return id;}
    public void setId(UUID id){this.id=id;}
    public String getNome(){return nome;}
    public void setNome(String n){this.nome=n;}
    public Integer getAnoLetivo(){return anoLetivo;}
    public void setAnoLetivo(Integer a){this.anoLetivo=a;}
}
