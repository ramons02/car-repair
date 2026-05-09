# CLAUDE.md

Este arquivo fornece diretrizes para o Claude Code (claude.ai/code) ao trabalhar com o código neste repositório.

## Visão Geral do Projeto

Car-Repair é um sistema full-stack de gerenciamento para oficina mecânica. O backend é uma REST API construída com Spring Boot 3.4 (Java 21) apoiada por um banco de dados Supabase/PostgreSQL. O frontend é uma SPA (Single Page Application) em Angular 21 com SSR habilitado e Tailwind CSS 4.

---

## Comandos

### Backend (executar a partir da raiz do repositório)

```bash
./mvnw spring-boot:run           # Iniciar a API na porta 9081
./mvnw clean package             # Construir o JAR
./mvnw clean test                # Executar todos os testes
./mvnw -q clean test-compile     # Verificar a compilação sem rodar os testes
```

### Frontend (executar a partir de `car-repair-frontend/`)

```bash
npm install                      # Instalar as dependências
npm start                        # Servidor de desenvolvimento (ng serve)
npm run build                    # Build para produção
npm test                         # Executar os testes com Vitest
```

### Documentação da API (com o backend rodando)
- Swagger UI: `http://localhost:9081/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:9081/api-docs`

---

## Arquitetura do Backend

### Camada Genérica (`core/`)

Toda a estrutura CRUD é implementada uma vez como classes genéricas abstratas. Todos os domínios de negócio estendem estas classes:

| Classe | Papel |
|---|---|
| `BaseModel` | JPA `@MappedSuperclass` — `id` (UUID), `ativo` (soft-delete), `dataHoraCriacao` |
| `IGenericRepository<E>` | Repositório JPA declarando `findByIdAndAtivoTrue` e `findAllByAtivoTrue` |
| `GenericService<E,R,V>` | Implementa `insert`, `update`, `delete` (soft), `findByIdActive`, `findAllActive`. Fornece hooks de ciclo de vida: `beforeInsert`, `afterInsert`, `beforeUpdate`, `afterUpdate`, `beforeDelete`, `afterDelete`. |
| `GenericValidation<E,R>` | Validação base com `validateFieldsInsert`, `validateFieldsUpdate`, `validateInsert`, `validateUpdate`, `validateDelete` |
| `GenericController<E,D,S,M>` | Expõe os endpoints REST: `GET /`, `GET /{id}`, `POST /`, `PUT /{id}`, `DELETE /{id}` |
| `IGenericMapper<E,D>` | Contrato do Mapper (`toEntity`, `toDto`, `toDtoPage`) — as implementações devem ser anotadas com `@Component` |

### Hierarquia de Exceções

- `BusinessException` — falha geral de regra de negócio; suporta `HttpStatus` customizado
- `FieldValidationException` — validação em nível de campo (retorna 400)
- `RuleValidationException` — validação cruzada entre campos / regra de negócio (retorna 422)
- `DataAccessExceptionHandler` — utilitário estático que converte violações de restrição do PostgreSQL (chave única → 409, FK → 422) antes que cheguem ao controller
- `GlobalExceptionHandler` — `@RestControllerAdvice` que formata todas as exceções em um `ErrorResponse`

### Domínios de Negócio (`business/`)

Quatro domínios seguem a mesma estrutura de pacotes: `clientes`, `veiculos`, `servicos`, `ordem_servicos`.

Cada domínio contém: `*Model` (entidade), `*DTO`, `*Mapper` (`@Component`), `I*Repository`, `I*Service`, `*Service`, `I*Validation`, `*Validation`, `*Controller`.

**Relacionamentos do Domínio:**
- `OrdemServicoModel` possui `@ManyToOne` tanto para `ClienteModel` quanto para `VeiculoModel`
- Banco de Dados: PostgreSQL (Supabase), `spring.jpa.hibernate.ddl-auto=update` (schema gerenciado automaticamente)

### Adicionando um Novo Domínio

1. Copie o pacote `clientes` como um modelo (template).
2. Implemente `*Model extends BaseModel`, `*DTO extends BaseDTO`, `*Mapper @Component` (implementa `IGenericMapper`).
3. Estenda `GenericValidation` para `*Validation`; estenda `GenericService` para `*Service`.
4. Estenda `GenericController` para `*Controller` com `@RestController` + `@RequestMapping`.

---

## Arquitetura do Frontend

Projeto Angular 21 usando standalone components com SSR (`@angular/ssr`) e Tailwind CSS 4.

### Estrutura

- `src/app/core/services/api.ts` — `ApiService`: cliente HTTP base englobando o `HttpClient` do Angular. A `API_URL` aponta para `http://localhost:8080/api` — **atualize isso para `http://localhost:9081`** para combinar com a porta do backend.
- `src/app/models/` — Interfaces do TypeScript que espelham os DTOs do backend (`ClienteModel`, `VeiculoModel`, `ServicoModel`, `OrdemServicoModel`).
- `src/app/features/` — Módulos de funcionalidade (`clientes`, `veiculos`, `servicos`, `ordem-servico`), cada um com um par de componentes `*-list` e `*-form`, seu próprio módulo de roteamento e NgModule.
- `src/app/shared/components/header/` — Componente de cabeçalho compartilhado.

### Testes

O frontend utiliza **Vitest** (e não Karma/Jest). Cada componente possui um arquivo `.spec.ts` junto a ele.

---

## Problemas Conhecidos / Notas

- A variável `ApiService.API_URL` em `core/services/api.ts` está fixada (hardcoded) para a porta `8080`; o backend roda na porta `9081`.
- O array raiz de rotas em `app.routes.ts` está atualmente vazio — módulos de roteamento de funcionalidade definem suas próprias rotas filhas, mas nenhuma está conectada à raiz ainda.
- `GenericService.delete()` contém um bug: ele chama `entity.setAtivo(true)` em vez de `entity.setAtivo(false)` para o soft-delete. Corrija antes de usar os endpoints de exclusão.

---


## Plano de Funcionalidade Atual

**Funcionalidade**: Telas Angular Car-Repair  
**Plano**: [specs/001-angular-ui-screens/plan.md](specs/001-angular-ui-screens/plan.md)  
**Spec**: [specs/001-angular-ui-screens/spec.md](specs/001-angular-ui-screens/spec.md)  

14 tarefas ordenadas em 5 fases. Comece com a FASE 1 (T-01 a T-06, infraestrutura) antes de qualquer trabalho na UI (interface de usuário).

