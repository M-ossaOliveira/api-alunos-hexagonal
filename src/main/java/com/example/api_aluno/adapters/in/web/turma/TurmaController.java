package com.example.api_aluno.adapters.in.web.turma;

import com.example.api_aluno.adapters.in.web.turma.dto.*;
import com.example.api_aluno.domain.turma.Turma;
import com.example.api_aluno.ports.in.TurmaUseCases;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.stream.Collectors;
import java.util.UUID;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Turmas", description = "Cadastro e manutenção de turmas")
@RestController
@RequestMapping("/turmas")
public class TurmaController{
    private final TurmaUseCases useCases;
    public TurmaController(TurmaUseCases u){this.useCases=u;}

    @Operation(
            summary = "Cria nova turma",
            description = "Cria uma turma com nome e ano letivo.",
            security = @SecurityRequirement(name = "bearer-jwt")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Turma criada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado (exige ROLE_ADMIN)"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @PostMapping
    public ResponseEntity<TurmaResponse> criar(@Valid @RequestBody TurmaRequest req)
    {
        Turma t=useCases.criar(req.getNome(),req.getAnoLetivo()); return ResponseEntity.status(HttpStatus.CREATED).body(new TurmaResponse(t.getId(),t.getNome(),t.getAnoLetivo()));}

    @Operation(
            summary = "Busca turma por ID",
            description = "Retorna os dados da turma se encontrada.",
            security = @SecurityRequirement(name = "bearer-jwt")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Encontrada"),
            @ApiResponse(responseCode = "404", description = "Não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado (exige ROLE_ADMIN)"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TurmaResponse> buscar(@PathVariable UUID id)
    {
        return useCases
                .buscarPorId(id)
                .map(
                        t->ResponseEntity.ok(
                                new TurmaResponse(t.getId(),t.getNome(),t.getAnoLetivo())
                                            )
                    ).orElseGet(()->ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Lista turmas",
            description = "Retorna todas as turmas cadastradas.",
            security = @SecurityRequirement(name = "bearer-jwt")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Acesso negado (exige ROLE_ADMIN)"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @GetMapping
    public java.util.List<TurmaResponse> listar()
    {
        return useCases
                .listar().stream().map(
                        t->new TurmaResponse(
                                t.getId(),t.getNome(),t.getAnoLetivo()
                        )
                                      ).collect(Collectors.toList());}

    @Operation(
            summary = "Atualiza turma",
            description = "Atualiza nome e ano letivo da turma informada.",
            security = @SecurityRequirement(name = "bearer-jwt")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Atualizada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado (exige ROLE_ADMIN)"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TurmaResponse> atualizar(@PathVariable UUID id,@Valid @RequestBody TurmaRequest req)
    {
        Turma t=useCases
                .atualizar(id,req.getNome(),req.getAnoLetivo());
        return ResponseEntity.ok(new TurmaResponse(t.getId(),t.getNome(),t.getAnoLetivo()));
    }

    @Operation(
            summary = "Exclui turma",
            description = "Exclui a turma pelo ID.",
            security = @SecurityRequirement(name = "bearer-jwt")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Excluída"),
            @ApiResponse(responseCode = "403", description = "Acesso negado (exige ROLE_ADMIN)"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable UUID id)
    {
        useCases.excluir(id); return ResponseEntity.noContent().build();
    }
}
