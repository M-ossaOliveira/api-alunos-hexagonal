package com.example.api_aluno.config;

import com.example.api_aluno.application.AlunoService;
import com.example.api_aluno.application.TurmaService;
import com.example.api_aluno.application.UsuarioService;
import com.example.api_aluno.ports.in.AlunoUseCases;
import com.example.api_aluno.ports.in.AuthUseCases;
import com.example.api_aluno.ports.in.TurmaUseCases;
import com.example.api_aluno.ports.out.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/*O que faz: expõe beans para os casos de uso (ports de entrada)
 * instanciando as implementações (UsuarioService etc).
 *Por que existe: mantém a aplicação mais explícita e modular,
 *sem depender da varredura automática de componentes em classes
 *do núcleo (application)
 *Quem o chama: o Spring ao inicializar o contexto. Os controllers
 *receberão as interfaces de use cases como dependências
 *(quando criarmos /auth).*/
@Configuration
public class ApplicationConfig {
    @Bean
    AuthUseCases authUseCases(UsuarioRepositoryPort usuarioRepositoryPort,
                              PasswordEncoderPort passwordEncoderPort,
                              TokenProviderPort tokenProviderPort) {
        return new UsuarioService(usuarioRepositoryPort, passwordEncoderPort, tokenProviderPort);
    }

    @Bean
    AlunoUseCases alunoUseCases(AlunoRepositoryPort alunoRepositoryPort,
                                TurmaRepositoryPort turmaRepositoryPort) {
        return new AlunoService(alunoRepositoryPort, turmaRepositoryPort);

    }

    @Bean
    TurmaUseCases turmaUseCases(TurmaRepositoryPort turmaRepositoryPort) {
        return new TurmaService(turmaRepositoryPort);
    }
}

