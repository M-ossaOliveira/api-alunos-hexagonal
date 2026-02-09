package com.example.api_aluno.adapters.in.web.auth.dto;

public class LoginResponse {
    private String token;
    public LoginResponse() {}
    public LoginResponse(String token) { this.token = token; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
