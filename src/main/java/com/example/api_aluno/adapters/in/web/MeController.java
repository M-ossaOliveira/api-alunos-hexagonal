package com.example.api_aluno.adapters.in.web;

import com.example.api_aluno.adapters.in.web.auth.dto.UsuarioResponse;
import com.example.api_aluno.domain.usuario.Usuario;
import com.example.api_aluno.ports.in.AuthUseCases;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/me")
public class MeController {

    private final AuthUseCases authUseCases;

    public MeController(AuthUseCases authUseCases) {
        this.authUseCases = authUseCases;
    }

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