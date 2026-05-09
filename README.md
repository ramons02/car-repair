# Car-Repair API

Uma API backend para um sistema de gerenciamento de oficina mecânica. Este projeto é construído com Spring Boot.

## Tecnologias Utilizadas

* **Java 21**
* **Spring Boot 3.4** (Web, Data JPA, Validation)
* **PostgreSQL / Supabase** para o banco de dados
* **Maven** para gerenciamento de dependências
* **Lombok** para redução de código boilerplate (repetitivo)
* **Swagger/OpenAPI** para documentação da API

---

## Pré-requisitos

Antes de executar o projeto, certifique-se de ter o seguinte instalado:
- **Java 21**
- **PostgreSQL** (ou use uma instância do Supabase conforme configurado)

---

## Como Rodar

A aplicação roda na porta `9081` por padrão.

1. Navegue até o diretório raiz do projeto.
2. Execute a aplicação usando o Maven Wrapper:
   ```bash
   ./mvnw spring-boot:run
   ```
3. Para rodar os testes:
   ```bash
   ./mvnw clean test
   ```

---

## Documentação da API

Com a aplicação rodando, você pode acessar a documentação interativa da API fornecida pelo Swagger UI:

- **Swagger UI:** [http://localhost:9081/swagger-ui/index.html](http://localhost:9081/swagger-ui/index.html)
- **OpenAPI JSON:** [http://localhost:9081/api-docs](http://localhost:9081/api-docs)

---

## Arquitetura do Projeto

### Padrão Core
O projeto utiliza uma arquitetura altamente genérica e reutilizável localizada no pacote `core/`, reduzindo a duplicação de código entre os diferentes domínios de negócio:
- **`BaseModel`**: Entidade base com ID, status ativo (para soft deletes/exclusão lógica) e data e hora de criação.
- **`IGenericRepository`**: Repositório JPA genérico com suporte a exclusão lógica.
- **`GenericService`**: Implementa as operações CRUD padrão e hooks de ciclo de vida (`beforeInsert`, `afterInsert`, etc.).
- **`GenericController`**: Expõe os endpoints REST padrão (`GET`, `POST`, `PUT`, `DELETE`).

### Domínios de Negócio
O sistema gerencia os seguintes domínios principais, todos estendendo a arquitetura genérica:
- **Clientes**
- **Veículos**
- **Serviços**
- **Ordens de Serviço**
