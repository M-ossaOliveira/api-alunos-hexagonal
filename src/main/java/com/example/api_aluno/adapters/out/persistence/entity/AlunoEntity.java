package com.example.api_aluno.adapters.out.persistence.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity @Table(name="alunos")
public class AlunoEntity {
    @Id @Column(name="id",nullable=false,updatable=false)
    private UUID id;
    @Column(nullable=false)
    private String nome;
    @Column(nullable=false,unique=true)
    private String email;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="turma_id")
    private TurmaEntity turma;
    public AlunoEntity(){}
    public AlunoEntity(UUID id,String nome,String email,TurmaEntity turma){
        this.id=id;this.nome=nome;this.email=email;this.turma=turma;
    }
    public UUID getId(){return id;}
    public void setId(UUID id){this.id=id;}
    public String getNome(){return nome;}
    public void setNome(String nome){this.nome=nome;}
    public String getEmail(){return email;}
    public void setEmail(String email){this.email=email;}
    public TurmaEntity getTurma(){return turma;}
    public void setTurma(TurmaEntity turma){this.turma=turma;}
}
