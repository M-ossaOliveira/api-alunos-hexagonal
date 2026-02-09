package com.example.api_aluno.adapters.out.security;

import com.example.api_aluno.ports.out.PasswordEncoderPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncoderAdapter implements PasswordEncoderPort {
    private final PasswordEncoder delegate = new BCryptPasswordEncoder();
    @Override
    public String encode(String rawPassword) {
        return delegate.encode(rawPassword);
    }
    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return delegate.matches(rawPassword, encodedPassword);
    }
}
