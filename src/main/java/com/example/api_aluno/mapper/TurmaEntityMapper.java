package com.example.api_aluno.mapper;

import com.example.api_aluno.adapters.out.persistence.entity.TurmaEntity;
import com.example.api_aluno.domain.turma.Turma;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TurmaEntityMapper{
    Turma toDomain(TurmaEntity e);
    TurmaEntity toEntity(Turma d);
}
