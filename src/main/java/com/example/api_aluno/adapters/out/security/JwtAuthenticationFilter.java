package com.example.api_aluno.adapters.out.security;

import com.example.api_aluno.domain.usuario.Perfil;
import com.example.api_aluno.domain.usuario.Usuario;
import com.example.api_aluno.ports.out.TokenProviderPort;
import com.example.api_aluno.ports.out.UsuarioRepositoryPort;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProviderPort tokenProvider;
    private final UsuarioRepositoryPort usuarioRepository;
    public JwtAuthenticationFilter(TokenProviderPort tokenProvider, UsuarioRepositoryPort usuarioRepository) {
        this.tokenProvider = tokenProvider;
        this.usuarioRepository = usuarioRepository;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if (tokenProvider.validateToken(token)) {
                String username = tokenProvider.getUsernameFromToken(token);
                usuarioRepository.buscarPorUsername(username).ifPresent(usuario -> {
                    UsernamePasswordAuthenticationToken auth = buildAuthentication(usuario);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                });
            }
        }
        filterChain.doFilter(request, response);
    }
    private UsernamePasswordAuthenticationToken buildAuthentication(Usuario usuario) {
        Set<GrantedAuthority> authorities = usuario.getPerfis() == null ? Set.of() : usuario.getPerfis().stream()
                .map(Perfil::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        return new UsernamePasswordAuthenticationToken(usuario.getUsername(), null, authorities);
    }
}
