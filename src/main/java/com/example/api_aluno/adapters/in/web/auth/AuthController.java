package com.example.api_aluno.adapters.in.web.auth;

import com.example.api_aluno.adapters.in.web.auth.dto.LoginRequest;
import com.example.api_aluno.adapters.in.web.auth.dto.LoginResponse;
import com.example.api_aluno.adapters.in.web.auth.dto.RegisterRequest;
import com.example.api_aluno.adapters.in.web.auth.dto.UsuarioResponse;
import com.example.api_aluno.domain.usuario.Usuario;
import com.example.api_aluno.ports.in.AuthUseCases;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;

@Tag(name = "Auth", description = "Autenticação e cadastro de usuários")
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthUseCases authUseCases;
    public AuthController(AuthUseCases authUseCases) {
        this.authUseCases = authUseCases;
    }

    @Operation(
            summary = "Registra um novo usuário",
            description = "Cria um usuário com username único e perfis (ROLE_USER, ROLE_ADMIN)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado",
                    content = @Content(schema = @Schema(implementation = UsuarioResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou username duplicado")
    })
    @PostMapping("/register")
    public ResponseEntity<UsuarioResponse> register(@RequestBody RegisterRequest request) {
        Usuario u = authUseCases.registrar(request.getUsername(), request.getPassword(), request.getPerfis());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new UsuarioResponse(u.getId(), u.getUsername(), u.getPerfis()));
    }

    @Operation(
            summary = "Autentica e retorna um token JWT",
            description = "Valida as credenciais e retorna um token JWT para uso nos demais endpoints protegidos."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login ok",
                    content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "Credenciais inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String token = authUseCases.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
