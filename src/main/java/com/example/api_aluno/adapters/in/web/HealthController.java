package com.example.api_aluno.adapters.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Tag(name = "Health", description = "Endpoints de verificação de saúde")
@RestController
@RequestMapping("/health")
public class HealthController {
    @Operation(
            summary = "Verifica a saúde da aplicação",
            description = "Retorna 200 OK e o corpo 'OK' se a aplicação estiver ativa."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Aplicação saudável")
    })
    @GetMapping
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("OK");
    }
}
