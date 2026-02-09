package com.example.api_aluno.adapters.in.web;

import com.example.api_aluno.adapters.in.web.auth.dto.UsuarioResponse;
import com.example.api_aluno.ports.in.AuthUseCases;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@Tag(name = "Me", description = "Informações do usuário autenticado")
@RestController
@RequestMapping("/me")
public class MeController {
    private final AuthUseCases authUseCases;
    public MeController(AuthUseCases authUseCases) {
        this.authUseCases = authUseCases;
    }

    @Operation(
            summary = "Obtém o usuário corrente",
            description = "Retorna informações do usuário autenticado (id, username, perfis).",
            security = @SecurityRequirement(name = "bearer-jwt")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping
    public ResponseEntity<UsuarioResponse> me(Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            return ResponseEntity.status(401).build();
        }
        return authUseCases.buscarPorUsername(authentication.getName())
                .map(u -> ResponseEntity.ok(new UsuarioResponse(u.getId(), u.getUsername(), u.getPerfis())))
                .orElseGet(() -> ResponseEntity.status(404).build());
    }
}
