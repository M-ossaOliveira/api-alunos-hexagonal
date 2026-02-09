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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AlunoController.class)
@Import(SecurityConfig.class)
class AlunoControllerTest {
    @Autowired MockMvc mvc;

    @MockBean AlunoUseCases useCases;
    // Beans exigidos pelo JwtAuthenticationFilter
    @MockBean TokenProviderPort tokenProviderPort;
    @MockBean UsuarioRepositoryPort usuarioRepositoryPort;

    @Test
    void criar_created_autenticado() throws Exception {
        // Mock do token JWT válido
        when(tokenProviderPort.validateToken("fake")).thenReturn(true);
        when(tokenProviderPort.getUsernameFromToken("fake")).thenReturn("mari");
        when(usuarioRepositoryPort.buscarPorUsername("mari"))
                .thenReturn(Optional.of(new Usuario(UUID.randomUUID(), "mari", "hash", Set.of(Perfil.ROLE_USER))));

        // Mock do caso de uso
        when(useCases.cadastrar(any(), any(), any()))
                .thenReturn(new Aluno(UUID.randomUUID(), "Ana", "ana@ex.com", null));

        // ✅ JSON correto (aspas internas escapadas; chaves sem escape)
        String json = "{\"nome\":\"Ana\",\"email\":\"ana@ex.com\"}";

        mvc.perform(post("/alunos")
                        .with(csrf())
                        .header("Authorization", "Bearer fake")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}