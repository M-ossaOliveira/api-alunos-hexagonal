package com.example.api_aluno.application;

import com.example.api_aluno.ports.in.AuthUseCases;

/** Implementa os casos de uso de autenticação definidos em AuthUseCases
 * Orquestra as regras da aplicação e delega as chamadas para as ports de saída:
 * -UsuarioRepositoryPort (persistência)
 * -PasswordEncoderPort (hash/validação de senha)
 * -TokenProviderPort(gerar/validar/parse de Token)
 * É chamado pelos controllers de auth*/
public class UsuarioService implements AuthUseCases {}
