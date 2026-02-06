package com.example.api_aluno.adapters.in.web.auth;

import com.example.api_aluno.adapters.in.web.auth.dto.LoginRequest;
import com.example.api_aluno.adapters.in.web.auth.dto.RegisterRequest;
import com.example.api_aluno.adapters.in.web.auth.dto.UsuarioResponse;
import com.example.api_aluno.domain.usuario.Perfil;
import com.example.api_aluno.domain.usuario.Usuario;
import com.example.api_aluno.ports.in.AuthUseCases;
import com.example.api_aluno.ports.out.TokenProviderPort;
import com.example.api_aluno.ports.out.UsuarioRepositoryPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Teste de slice MVC do AuthController mantendo a Security ativa:
 * - Importa o SecurityConfig real;
 * - Mantém o JwtAuthenticationFilter no contexto;
 * - Mocka apenas as portas de saída usadas pelo filtro;
 * - Usa .with(csrf()) em POST.
 */
@WebMvcTest(controllers = AuthController.class)
@Import(com.example.api_aluno.adapters.out.security.config.SecurityConfig.class)
class AuthControllerTest {

    @Autowired
    MockMvc mvc;

    // Use case do controller mockado (foco do teste é o controller)
    @MockBean
    AuthUseCases authUseCases;

    // Beans exigidos pelo JwtAuthenticationFilter (mantemos o filtro real no contexto)
    @MockBean
    TokenProviderPort tokenProviderPort;

    @MockBean
    UsuarioRepositoryPort usuarioRepositoryPort;

    @Test
    void register_created() throws Exception {
        when(authUseCases.registrar(eq("mari"), eq("123"), any()))
                .thenReturn(new Usuario(UUID.randomUUID(), "mari", "hash", Set.of(Perfil.ROLE_USER)));

        String json = "{\"username\":\"mari\",\"password\":\"123\",\"perfis\":[\"ROLE_USER\"]}";

        mvc.perform(post("/auth/register")
                        .with(csrf()) // importante em POST mantendo segurança ativa
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void login_ok() throws Exception {
        when(authUseCases.login("mari", "123")).thenReturn("token");

        String json = "{\"username\":\"mari\",\"password\":\"123\"}";

        mvc.perform(post("/auth/login")
                        .with(csrf()) // importante em POST mantendo segurança ativa
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token"));
    }
}