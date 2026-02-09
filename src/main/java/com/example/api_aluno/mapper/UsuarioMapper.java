package com.example.api_aluno.mapper;

import com.example.api_aluno.adapters.out.persistence.entity.UsuarioEntity;
import com.example.api_aluno.domain.usuario.Usuario;

/*Converte
* Usuario(domain) <-> UsuarioEntity (JPA)
* nas duas direções.
*
* Existe para manter o domínio isolado das anotações/estratégias
* de banco e permitir evoluir a persistência sem afetar o núcleo
*
* Quem o chama é o Adapter do Repositório*/

public final class UsuarioMapper {
    private UsuarioMapper() {}
    public static UsuarioEntity toEntity(Usuario u) {
        if (u == null) return null;
        return new UsuarioEntity(u.getId(), u.getUsername(), u.getSenhaHash(), u.getPerfis());
    }
    public static Usuario toDomain(UsuarioEntity e) {
        if (e == null) return null;
        return new Usuario(e.getId(), e.getUsername(), e.getSenhaHash(), e.getPerfis());
    }
}