package com.example.api_aluno.adapters.in.web.aluno;

import com.example.api_aluno.adapters.in.web.aluno.dto.*;
import com.example.api_aluno.domain.aluno.Aluno;
import com.example.api_aluno.ports.in.AlunoUseCases;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.UUID;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Alunos", description = "Cadastro, consulta, filtro paginado e exportações")
@RestController
@RequestMapping("/alunos")
public class AlunoController{
    private final AlunoUseCases useCases;
    public AlunoController(AlunoUseCases u){this.useCases=u;}

    @Operation(
            summary = "Cria um novo aluno",
            description = "Cria um aluno com nome, e-mail e (opcionalmente) turmaId.",
            security = @SecurityRequirement(name = "bearer-jwt")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Aluno criado",
                    content = @Content(schema = @Schema(implementation = AlunoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @PostMapping
    public ResponseEntity<AlunoResponse> criar(@Valid @RequestBody AlunoRequest req) {
        Aluno a = useCases.cadastrar(req.getNome(), req.getEmail(), req.getTurmaId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AlunoResponse(a.getId(), a.getNome(), a.getEmail(), a.getTurmaId()));
    }

    @Operation(
            summary = "Busca aluno por ID",
            description = "Retorna os dados do aluno se encontrado.",
            security = @SecurityRequirement(name = "bearer-jwt")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Encontrado",
                    content = @Content(schema = @Schema(implementation = AlunoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponse> buscar(@PathVariable UUID id) {
        return useCases.buscarPorId(id)
                .map(a -> ResponseEntity.ok(new AlunoResponse(a.getId(), a.getNome(), a.getEmail(), a.getTurmaId())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Lista todos os alunos",
            description = "Retorna a lista completa de alunos.",
            security = @SecurityRequirement(name = "bearer-jwt")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping
    public List<AlunoResponse> listar() {
        return useCases.listar().stream()
                .map(a -> new AlunoResponse(a.getId(), a.getNome(), a.getEmail(), a.getTurmaId()))
                .collect(Collectors.toList());
    }

    @Operation(
            summary = "Atualiza aluno",
            description = "Atualiza nome, e-mail e turmaId do aluno informado.",
            security = @SecurityRequirement(name = "bearer-jwt")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Atualizado",
                    content = @Content(schema = @Schema(implementation = AlunoResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AlunoResponse> atualizar(@PathVariable UUID id,@Valid @RequestBody AlunoRequest req){
        Aluno a=useCases
                .atualizar(id,req.getNome(),req.getEmail(),req.getTurmaId());
        return ResponseEntity.ok(
                new AlunoResponse(a.getId(),a.getNome(),a.getEmail(),a.getTurmaId())
                                );
    }

    @Operation(
            summary = "Exclui aluno",
            description = "Exclui o aluno pelo ID.",
            security = @SecurityRequirement(name = "bearer-jwt")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Excluída"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable UUID id){
        useCases.excluir(id);
        return ResponseEntity.noContent().build();
    }

    // ---- NOVOS ENDPOINTS / EXISTENTES ----
    @Operation(
            summary = "Filtra alunos (paginado)",
            description = "Filtra por nome e/ou e-mail. Retorna página de resultados.",
            security = @SecurityRequirement(name = "bearer-jwt")
    )
    @Parameters({
            @Parameter(name = "nome", description = "Filtro por nome (contém, case-insensitive)"),
            @Parameter(name = "email", description = "Filtro por e-mail (contém, case-insensitive)"),
            @Parameter(name = "page", description = "Número da página (0..N)"),
            @Parameter(name = "size", description = "Tamanho da página"),
            @Parameter(name = "sort", description = "Ordenação, ex: nome,asc")
    })
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "OK") })
    @GetMapping("/filtro")
    public ResponseEntity<Page<AlunoResponse>> filtrar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String email,
            @PageableDefault(size = 20, sort = "nome") Pageable pageable
                                                      ){
        Page<AlunoResponse> page = useCases.filtrar(nome, email, pageable)
                .map(a -> new AlunoResponse(a.getId(), a.getNome(), a.getEmail(), a.getTurmaId()));
        return ResponseEntity.ok(page);
    }

    @Operation(
            summary = "Exporta alunos em CSV",
            description = "Gera um arquivo CSV (attachment) com colunas: id;nome;email;turmaId.",
            security = @SecurityRequirement(name = "bearer-jwt")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Arquivo CSV",
                    content = @Content(mediaType = "text/csv")),
            @ApiResponse(responseCode = "403", description = "Acesso negado (exige ROLE_ADMIN)"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @GetMapping(value = "/export/csv", produces = "text/csv")
    public void exportCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=alunos.csv");
        var alunos = useCases.listar();
        try (var writer = response.getWriter()) {
            writer.println("id;nome;email;turmaId");
            for (var a : alunos) {
                writer.printf("%s;%s;%s;%s%n",
                        a.getId(), a.getNome(), a.getEmail(),
                        a.getTurmaId() == null ? "" : a.getTurmaId().toString());
            }
        }
    }

    @Operation(
            summary = "Exporta alunos em XLSX",
            description = "Gera um arquivo Excel (attachment) com colunas: ID, Nome, Email, TurmaId.",
            security = @SecurityRequirement(name = "bearer-jwt")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Arquivo XLSX",
                    content = @Content(mediaType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")),
            @ApiResponse(responseCode = "403", description = "Acesso negado (exige ROLE_ADMIN)"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @GetMapping(value = "/export/xlsx", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public void exportXlsx(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=alunos.xlsx");
        var alunos = useCases.listar();
        try (var wb = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {
            var sheet = wb.createSheet("Alunos");
            int r = 0;
            var header = sheet.createRow(r++);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Nome");
            header.createCell(2).setCellValue("Email");
            header.createCell(3).setCellValue("TurmaId");
            for (var a : alunos) {
                var row = sheet.createRow(r++);
                row.createCell(0).setCellValue(a.getId().toString());
                row.createCell(1).setCellValue(a.getNome());
                row.createCell(2).setCellValue(a.getEmail());
                row.createCell(3).setCellValue(a.getTurmaId() == null ? "" : a.getTurmaId().toString());
            }
            for (int c=0; c<4; c++) sheet.autoSizeColumn(c);
            try (var out = response.getOutputStream()) { wb.write(out); }
        }
    }
}
