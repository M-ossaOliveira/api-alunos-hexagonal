# API de Alunos

Este projeto é uma API para gestão de alunos, ideal para evolução de devs juniores.

## Roadmap de Evolução
## Estrutura de Pastas Recomendada
### Exemplos de Arquivos/Subclasses em Cada Pasta

**domain/**
- Aluno.java (entidade principal)

**application/**
- AlunoService.java (caso de uso: regras de negócio)

**ports/**
- AlunoSalvarUseCase.java (interface de caso de uso)
- AlunoConsultarUseCase.java (interface de caso de uso)

**adapters/**
- AlunoController.java (REST Controller)
- JpaAlunoRepository.java (implementação JPA do repositório)

**dto/**
- AlunoDTO.java (DTO para entrada/saída de dados)

**resources/**
- application.properties (configurações do projeto)

**test/**
- AlunoServiceTest.java (testes de unidade)
- AlunoControllerTest.java (testes de integração)

```
src/
	main/
		java/
			com/example/alunos/
				domain/      # Entidades e Value Objects
				application/ # Casos de uso (serviços do domínio)
				ports/       # Interfaces (entrada/saída)
				adapters/    # Controllers, Repositórios, etc
				dto/         # Data Transfer Objects
		resources/       # Configurações e arquivos de recursos
	test/
		java/
			com/example/alunos/ # Testes automatizados
```

### 1. Domínio
- Criar entidade Aluno (id, nome, email, dataNascimento)
- Adicionar atributos e métodos para suporte a filtros (ex: por nome/email)
- Criar entidade Turma (id, nome, anoLetivo)
- Definir relacionamento: Aluno pertence a uma Turma (muitos para um)
    - Aluno: atributo turmaId ou referência para Turma
    - Turma: lista de alunos (opcional, para navegação reversa)

### 2. Persistência
- Adicionar JPA/Hibernate
- Criar repositório para Aluno
- Implementar métodos de busca com filtro por nome/email
- Implementar persistência dos dados de desempenho

### 3. Segurança
- Implementar autenticação (Login)
- Adicionar perfis de acesso (ROLE_USER, ROLE_ADMIN)
- Garantir que exportação de dados e relatórios só sejam acessíveis por perfis autorizados

### 4. Auditoria
- ND

### 5. Testes
- Cobertura mínima de 95% com JUnit 5 + Mockito
- Testar filtros, exportação de dados e geração de relatórios

### 6. Documentação
- Adicionar Swagger/OpenAPI
- Documentar endpoints de filtro, exportação e relatórios


## Entendendo a Arquitetura Hexagonal

A Arquitetura Hexagonal (Ports and Adapters) separa o núcleo da aplicação (regras de negócio) das tecnologias externas (banco, web, etc):
- **Domínio/Core:** Entidades e regras de negócio, sem dependências de frameworks.
- **Ports:** Interfaces que definem contratos para entrada (casos de uso) e saída (persistência, serviços externos).
- **Adapters:** Implementações concretas das ports, como Controllers REST (entrada) e Repositórios JPA (saída).

**Vantagens:**
- Facilita testes e manutenção.
- Permite trocar tecnologias sem afetar o núcleo.

## Entities vs DTOs

- **Entities:** Representam os dados e regras de negócio do domínio. Exemplo: `Aluno` (id, nome, email, dataNascimento).
- **DTOs (Data Transfer Objects):** Usados para transportar dados entre camadas (ex: entre API e domínio). Não possuem regras de negócio, apenas dados.

**Quando usar:**
- Use Entities no núcleo do domínio e para persistência.
- Paginação de resultados
- Validação de dados na entrada
- Cadastro e consulta de aluno e turmas
- Associação de alunos a turmas
- Use DTOs para entrada/saída de dados na API (Controllers), evitando expor Entities diretamente.

## Features Sugeridas
- Filtro por nome/email
- Exportação de dados para CSV/Excel