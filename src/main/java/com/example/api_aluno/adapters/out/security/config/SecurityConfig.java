package com.example.api_aluno.adapters.out.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
/*Registra um SecurityFilterChain dizendo como o SpringSecurity deve
* se comportar*/
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Estamos sem autenticação por enquanto, então:
        http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // libera tudo por enquanto
                                  );
        // Desativa as telas/prompt padrão
        http.formLogin(login -> login.disable());
        http.httpBasic(basic -> basic.disable());
        return http.build();
    }
}