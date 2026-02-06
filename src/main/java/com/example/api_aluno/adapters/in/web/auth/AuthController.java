package com.example.api_aluno.adapters.in.web.auth;

import com.example.api_aluno.adapters.in.web.auth.dto.*;
import com.example.api_aluno.domain.usuario.Usuario;
import com.example.api_aluno.ports.in.AuthUseCases;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthUseCases authUseCases;

    public AuthController(AuthUseCases authUseCases) {
        this.authUseCases = authUseCases;
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponse> register(@RequestBody RegisterRequest request) {
        try {
            Usuario u = authUseCases.registrar(request.getUsername(), request.getPassword(), request.getPerfis());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new UsuarioResponse(u.getId(), u.getUsername(), u.getPerfis()));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            String token = authUseCases.login(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(new LoginResponse(token));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
