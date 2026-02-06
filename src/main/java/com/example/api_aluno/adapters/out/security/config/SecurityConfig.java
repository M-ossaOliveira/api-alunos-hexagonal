package com.example.api_aluno.adapters.out.security.config;

import com.example.api_aluno.adapters.out.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtFilter) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin())); // H2 console

        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/health", "/h2-console/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .anyRequest().authenticated()
                                  );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.formLogin(login -> login.disable());
        http.httpBasic(basic -> basic.disable());

        return http.build();
    }
}