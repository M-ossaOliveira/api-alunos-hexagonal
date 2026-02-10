package com.example.api_aluno.adapters.in.web.turma.dto;
import jakarta.validation.constraints.*;

public class TurmaRequest{
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9._-]{3,30}$",
            message = "nome da Turma deve conter 3-30 chars (letras, números, . _ -)")
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
