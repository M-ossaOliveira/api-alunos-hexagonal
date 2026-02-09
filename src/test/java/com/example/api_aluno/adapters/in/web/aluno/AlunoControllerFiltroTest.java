
package com.example.api_aluno.adapters.in.web.aluno;

import com.example.api_aluno.adapters.out.security.config.SecurityConfig;
import com.example.api_aluno.domain.aluno.Aluno;
import com.example.api_aluno.domain.usuario.*;
import com.example.api_aluno.ports.in.AlunoUseCases;
import com.example.api_aluno.ports.out.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AlunoController.class)
@Import(SecurityConfig.class)
class AlunoControllerFiltroTest {
    @Autowired MockMvc mvc;
    @MockBean AlunoUseCases useCases;
    // Beans exigidos pelo JwtAuthenticationFilter
    @MockBean TokenProviderPort tokenProviderPort;
    @MockBean UsuarioRepositoryPort usuarioRepositoryPort;

    @Test
    void filtrar_ok() throws Exception {
        when(tokenProviderPort.validateToken("fake")).thenReturn(true);
        when(tokenProviderPort.getUsernameFromToken("fake")).thenReturn("admin");
        when(usuarioRepositoryPort.buscarPorUsername("admin")).thenReturn(
                Optional.of(new Usuario(UUID.randomUUID(), "admin", "hash", Set.of(Perfil.ROLE_USER)))
                                                                         );

        var pageable = PageRequest.of(0, 2);
        var a = new Aluno(UUID.randomUUID(), "Ana", "ana@ex.com", null);
        when(useCases.filtrar(eq("ana"), eq(null), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(a), pageable, 1));

        mvc.perform(get("/alunos/filtro?nome=ana&page=0&size=2")
                        .header("Authorization", "Bearer fake")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nome").value("Ana"))
                .andExpect(jsonPath("$.totalElements").value(1));
    }
}
