package com.example.api_aluno.adapters.out.persistence.entity;
import jakarta.persistence.*;
import java.util.*;
@Entity @Table(name="turmas")
public class TurmaEntity {
    @Id @Column(name="id",nullable=false,updatable=false)
    private UUID id;
    @Column(nullable=false)
    private String nome;
    @Column(name="ano_letivo",nullable=false)
    private Integer anoLetivo;
    @OneToMany(mappedBy="turma", cascade=CascadeType.ALL)
    private Set<AlunoEntity> alunos=new HashSet<>();

    public TurmaEntity(){}
    public TurmaEntity(UUID id,String nome,Integer anoLetivo){
        this.id=id;this.nome=nome;this.anoLetivo=anoLetivo;
    }
    public UUID getId(){return id;} public void setId(UUID id){
        this.id=id;
    }
    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome=nome;
    }
    public Integer getAnoLetivo(){
        return anoLetivo;
    }
    public void setAnoLetivo(Integer a){
        this.anoLetivo=a;
    }
    public Set<AlunoEntity> getAlunos(){
        return alunos;
    }
    public void setAlunos(Set<AlunoEntity> s){
        this.alunos=s;
    }
}
