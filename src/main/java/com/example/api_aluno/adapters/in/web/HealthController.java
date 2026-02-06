package com.example.api_aluno.adapters.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*O SpringMVC o chama qd chega uma requisição HTTP nesta rota*/

@RestController
@RequestMapping("/health")
public class HealthController {
    @GetMapping
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("OK");
    }
}
