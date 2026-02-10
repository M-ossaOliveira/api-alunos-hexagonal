package com.example.api_aluno.adapters.out.security.config;

import com.example.api_aluno.adapters.out.security.JwtAuthenticationFilter;
import com.example.api_aluno.ports.out.TokenProviderPort;
import com.example.api_aluno.ports.out.UsuarioRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter(TokenProviderPort tokenProviderPort,
                                                    UsuarioRepositoryPort usuarioRepositoryPort) {
        return new JwtAuthenticationFilter(tokenProviderPort, usuarioRepositoryPort);
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtFilter) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));
        http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/health", "/h2-console/**",
                                "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/alunos/export/**", "/turmas/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                                  );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        // Handlers JSON para 401/403 quando o bloqueio ocorrer antes de chegar ao controller
        http.exceptionHandling(ex -> ex
                        .authenticationEntryPoint((req, res, e) -> {
                            res.setStatus(HttpStatus.UNAUTHORIZED.value());
                            res.setContentType("application/json");
                            byte[] body = "{\"error\":\"Não autenticado\"}".getBytes(StandardCharsets.UTF_8);
                            res.getOutputStream().write(body);
                        })
                        .accessDeniedHandler((req, res, e) -> {
                            res.setStatus(HttpStatus.FORBIDDEN.value());
                            res.setContentType("application/json");
                            byte[] body = "{\"error\":\"Acesso negado\"}".getBytes(StandardCharsets.UTF_8);
                            res.getOutputStream().write(body);
                        })
                              );

        http.formLogin(login -> login.disable());
        http.httpBasic(basic -> basic.disable());
        return http.build();
    }
}
