package com.example.api_aluno.adapters.in.web.turma.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public class TurmaRequest {

    @NotBlank
    @Schema(
            description = "Nome da turma",
            example = "Algoritmos I - Noturno"
    )
    @Pattern(
            regexp = "^[\\p{L}\\p{M}0-9 ._-]{3,50}$",
            message = "nome da Turma deve conter de 3 a 50 caracteres (letras, números, espaço, ., _, -)"
    )
    private String nome;

    @NotNull
    private Integer anoLetivo;

    public String getNome(){ return nome; }
    public void setNome(String n){ this.nome=n; }
    public Integer getAnoLetivo(){ return anoLetivo; }
    public void setAnoLetivo(Integer a){ this.anoLetivo=a; }
}