package com.example.api_aluno.adapters.in.web.turma.dto;
import jakarta.validation.constraints.*;

public class TurmaRequest{
    @NotBlank
    private String nome;
    @NotNull
    private Integer anoLetivo;

    public String getNome(){
        return nome;
    }
    public void setNome(String n){
        this.nome=n;
    }
    public Integer getAnoLetivo(){
        return anoLetivo;
    }
    public void setAnoLetivo(Integer a){
        this.anoLetivo=a;
    }
}
