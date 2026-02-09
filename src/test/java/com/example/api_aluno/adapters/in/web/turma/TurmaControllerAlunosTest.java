package com.example.api_aluno.adapters.in.web.turma;

import com.example.api_aluno.adapters.out.security.config.SecurityConfig;
import com.example.api_aluno.adapters.in.web.aluno.dto.AlunoResponse;
import com.example.api_aluno.domain.aluno.Aluno;
import com.example.api_aluno.domain.usuario.*;
import com.example.api_aluno.ports.in.AlunoUseCases;
import com.example.api_aluno.ports.in.TurmaUseCases;
import com.example.api_aluno.ports.out.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.*;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TurmaController.class)
@Import(SecurityConfig.class)
class TurmaControllerAlunosTest {
    @Autowired MockMvc mvc;
    @MockBean TurmaUseCases turmaUseCases; // não usado diretamente no endpoint, mas exigido pelo construtor
    @MockBean AlunoUseCases alunoUseCases;
    // Beans exigidos pelo JwtAuthenticationFilter
    @MockBean TokenProviderPort tokenProviderPort;
    @MockBean UsuarioRepositoryPort usuarioRepositoryPort;

    @Test
    void alunos_da_turma_admin_ok() throws Exception {
        when(tokenProviderPort.validateToken("fake")).thenReturn(true);
        when(tokenProviderPort.getUsernameFromToken("fake")).thenReturn("admin");
        when(usuarioRepositoryPort.buscarPorUsername("admin")).thenReturn(
                Optional.of(new Usuario(UUID.randomUUID(), "admin", "hash", Set.of(Perfil.ROLE_ADMIN)))
                                                                         );
        UUID turmaId = UUID.randomUUID();
        when(alunoUseCases.listarPorTurma(turmaId)).thenReturn(List.of(
                new Aluno(UUID.randomUUID(), "Ana", "ana@ex.com", turmaId)
                                                                      ));
        mvc.perform(get("/turmas/"+turmaId+"/alunos")
                        .header("Authorization", "Bearer fake")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Ana"));
    }
}
