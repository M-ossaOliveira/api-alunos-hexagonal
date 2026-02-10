
package com.example.api_aluno.adapters.in.web.aluno;

import com.example.api_aluno.adapters.out.security.config.SecurityConfig;
import com.example.api_aluno.domain.aluno.Aluno;
import com.example.api_aluno.domain.usuario.*;
import com.example.api_aluno.ports.in.AlunoUseCases;
import com.example.api_aluno.ports.out.*;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AlunoController.class)
@Import(SecurityConfig.class)
class AlunoExportControllerTest {
    @Autowired MockMvc mvc;
    @MockBean AlunoUseCases useCases;
    // Beans exigidos pelo @OneToMany($params$)
    @MockBean TokenProviderPort tokenProviderPort;
    @MockBean UsuarioRepositoryPort usuarioRepositoryPort;

    private void mockAdmin(){
        when(tokenProviderPort.validateToken("fake")).thenReturn(true);
        when(tokenProviderPort.getUsernameFromToken("fake")).thenReturn("admin");
        when(usuarioRepositoryPort.buscarPorUsername("admin")).thenReturn(
                Optional.of(new Usuario(UUID.randomUUID(), "admin", "hash", Set.of(Perfil.ROLE_ADMIN)))
                                                                         );
    }

    @Test
    void export_csv_admin_ok() throws Exception {
        mockAdmin();
        when(useCases.listar()).thenReturn(List.of(
                new Aluno(UUID.randomUUID(), "Ana", "ana@ex.com", null)
                                                  ));
        mvc.perform(get("/alunos/export/csv")
                        .header("Authorization", "Bearer fake")
                        .accept("text/csv"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", Matchers.containsString("alunos.csv")));
    }

    @Test
    void export_xlsx_admin_ok() throws Exception {
        mockAdmin();
        when(useCases.listar()).thenReturn(List.of(
                new Aluno(UUID.randomUUID(), "Ana", "ana@ex.com", null)
                                                  ));

        mvc.perform(get("/alunos/export/xlsx")
                        .header("Authorization", "Bearer fake")
                        // peça o mesmo media type que o controller PRODUZ
                        .accept("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", Matchers.containsString("alunos.xlsx")))
                .andExpect(header().string("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));

    }
}
