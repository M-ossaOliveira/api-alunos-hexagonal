package com.example.api_aluno.mapper;
import com.example.api_aluno.adapters.out.persistence.entity.*;
import com.example.api_aluno.domain.aluno.Aluno;
import org.mapstruct.*;import java.util.UUID;

@Mapper(componentModel="spring")
public interface AlunoEntityMapper{
    @Mapping(target="turmaId", source="turma.id")
    Aluno toDomain(AlunoEntity e);
    @Mapping(target="turma", source="turmaId", qualifiedByName="toTurmaEntity")
    AlunoEntity toEntity(Aluno a);
    @Named("toTurmaEntity")
    default TurmaEntity toTurmaEntity(UUID turmaId){
        if(turmaId==null) return null;
        TurmaEntity t=new TurmaEntity();
        t.setId(turmaId);
        return t;
    }
}
