# Feature Specification: Telas do Sistema Car-Repair

**Feature ID**: 001  
**Short Name**: angular-ui-screens  
**Status**: Draft  
**Created**: 2026-05-07  

---

## Summary

Construção das telas funcionais do sistema de gestão de oficina mecânica Car-Repair. O frontend Angular já possui a estrutura de módulos e componentes criada, mas todos os componentes são placeholders sem funcionalidade. Esta feature entrega as telas completas: Menu de Navegação, Clientes, Veículos, Serviços e Ordens de Serviço — todas integradas com a API REST do backend.

---

## Problem Statement

O sistema Car-Repair possui um backend completo com endpoints REST para gerenciar clientes, veículos, serviços e ordens de serviço, mas o frontend não apresenta nenhuma tela funcional. Os operadores da oficina não conseguem usar o sistema via navegador para cadastrar clientes, registrar veículos, criar ordens de serviço ou consultar serviços realizados.

---

## Goals

- Permitir que operadores da oficina gerenciem clientes, veículos, serviços e ordens de serviço via interface web.
- Eliminar a dependência de ferramentas técnicas (ex: Swagger/Postman) para operar o sistema.
- Fornecer navegação intuitiva entre todas as áreas do sistema através de um menu principal.

---

## Scope

### In Scope

- **Menu de Navegação**: barra lateral ou superior com links para todas as seções do sistema.
- **Tela de Clientes**: listagem, cadastro, edição e exclusão de clientes.
- **Tela de Veículos**: listagem, cadastro, edição e exclusão de veículos.
- **Tela de Serviços**: listagem, cadastro, edição e exclusão de serviços.
- **Tela de Ordens de Serviço**: listagem, cadastro, edição e exclusão de ordens de serviço, com associação a cliente e veículo.
- Correção do URL da API (`8080` → `9081`).
- Configuração das rotas da aplicação (`app.routes.ts`).
- Integração de todos os componentes com a `ApiService`.

### Out of Scope

- Autenticação e controle de acesso de usuários.
- Relatórios e dashboards analíticos.
- Upload de imagens ou documentos.
- Notificações ou alertas em tempo real.
- Aplicativo mobile.

---

## User Scenarios & Testing

### Cenário 1: Operador cadastra um novo cliente
**Dado** que o operador está na tela de Clientes  
**Quando** clica em "Novo Cliente", preenche nome, CPF, telefone e endereço, e confirma  
**Então** o cliente aparece na listagem e os dados estão salvos no sistema

### Cenário 2: Operador edita dados de um cliente existente
**Dado** que existe uma lista de clientes cadastrados  
**Quando** o operador clica em "Editar" em um cliente e altera o telefone  
**Então** o telefone atualizado é exibido na listagem imediatamente

### Cenário 3: Operador exclui um cliente inativo
**Dado** que o cliente não possui ordens de serviço abertas  
**Quando** o operador clica em "Excluir" e confirma a ação  
**Então** o cliente é removido da listagem (soft-delete)

### Cenário 4: Operador cadastra um veículo vinculado a um cliente
**Dado** que o operador está na tela de Veículos  
**Quando** preenche marca, modelo, ano, placa, cor e confirma  
**Então** o veículo aparece na listagem de veículos

### Cenário 5: Operador abre uma ordem de serviço
**Dado** que existem clientes e veículos cadastrados  
**Quando** o operador vai à tela de Ordens de Serviço, clica em "Nova Ordem", seleciona um cliente, seleciona um veículo, descreve o problema e confirma  
**Então** a ordem de serviço é criada e aparece na listagem com data de entrada registrada

### Cenário 6: Operador consulta ordens de serviço existentes
**Dado** que há ordens de serviço cadastradas  
**Quando** o operador acessa a tela de Ordens de Serviço  
**Então** vê a lista com cliente, veículo, problema e data de entrada de cada ordem

### Cenário 7: Operador cadastra um serviço em uma ordem
**Dado** que existe uma ordem de serviço aberta  
**Quando** o operador acessa a tela de Serviços, adiciona descrição, mecânico responsável, hora início/fim e valor  
**Então** o serviço fica registrado e vinculado à ordem de serviço

### Cenário 8: Operador navega entre as telas
**Dado** que o operador está em qualquer tela do sistema  
**Quando** clica em um item do menu (Clientes, Veículos, Serviços, Ordens)  
**Então** é direcionado para a tela correspondente sem recarregar a página

### Cenário 9: Erro de validação ao salvar formulário incompleto
**Dado** que o operador está preenchendo um formulário  
**Quando** tenta salvar com campos obrigatórios em branco  
**Então** o sistema exibe mensagens indicando quais campos precisam ser preenchidos

### Cenário 10: Erro de comunicação com o servidor
**Dado** que o backend está indisponível  
**Quando** o operador tenta carregar uma listagem  
**Então** o sistema exibe uma mensagem de erro amigável em vez de uma tela em branco

---

## Functional Requirements

### FR-01: Menu de Navegação
- O sistema deve exibir um menu visível em todas as telas com links para: Clientes, Veículos, Serviços e Ordens de Serviço.
- O item de menu da tela ativa deve ser visualmente destacado.
- O menu deve ser responsivo e acessível em diferentes tamanhos de tela.

### FR-02: Tela de Clientes
- FR-02.1: Exibir listagem de todos os clientes ativos com colunas: Nome, CPF, Telefone, Cidade.
- FR-02.2: Permitir cadastro de novo cliente com campos obrigatórios: Nome, CPF, Telefone, Endereço, Bairro, Cidade, Estado, CEP.
- FR-02.3: Permitir edição dos dados de um cliente existente.
- FR-02.4: Permitir exclusão de um cliente (com confirmação antes de excluir).
- FR-02.5: Exibir mensagem de erro se CPF já estiver cadastrado.

### FR-03: Tela de Veículos
- FR-03.1: Exibir listagem de todos os veículos ativos com colunas: Placa, Marca, Modelo, Ano, Cor.
- FR-03.2: Permitir cadastro de novo veículo com campos obrigatórios: Marca, Modelo, Ano de Fabricação, Ano do Modelo, Placa, Cor.
- FR-03.3: Permitir edição dos dados de um veículo existente.
- FR-03.4: Permitir exclusão de um veículo (com confirmação).
- FR-03.5: Exibir mensagem de erro se placa já estiver cadastrada.

### FR-04: Tela de Serviços
- FR-04.1: Exibir listagem de serviços com colunas: Descrição, Mecânico, Data/Hora Início, Valor.
- FR-04.2: Permitir cadastro de novo serviço com campos obrigatórios: Descrição, Mecânico, Data/Hora Início, Data/Hora Término, Valor. Campo opcional: vínculo com Ordem de Serviço.
- FR-04.3: Permitir edição de um serviço existente.
- FR-04.4: Permitir exclusão de um serviço (com confirmação).

### FR-05: Tela de Ordens de Serviço
- FR-05.1: Exibir listagem de ordens de serviço com colunas: Número/ID, Cliente, Veículo (placa), Problema, Data de Entrada.
- FR-05.2: Permitir criação de nova ordem de serviço com campos obrigatórios: Cliente (selecionado de lista), Veículo (selecionado de lista), Data de Entrada, Problema. Campo opcional: Observações.
- FR-05.3: O campo "Data de Saída" pode ser preenchido ao encerrar a ordem.
- FR-05.4: Permitir edição de uma ordem de serviço existente.
- FR-05.5: Permitir exclusão de uma ordem de serviço (com confirmação).

### FR-06: Navegação e Rotas
- FR-06.1: A aplicação deve ter rotas configuradas para todas as telas: `/clientes`, `/veiculos`, `/servicos`, `/ordens-servico`.
- FR-06.2: A rota raiz `/` deve redirecionar para a tela de Ordens de Serviço (tela principal da oficina).
- FR-06.3: Rotas inexistentes devem exibir página de "não encontrado" ou redirecionar para a rota raiz.

### FR-07: Integração com API
- FR-07.1: A URL da API deve apontar para `http://localhost:9081`.
- FR-07.2: Todas as operações de listagem devem utilizar `GET /api/{recurso}`.
- FR-07.3: Todas as operações de criação devem utilizar `POST /api/{recurso}`.
- FR-07.4: Todas as operações de edição devem utilizar `PUT /api/{recurso}/{id}`.
- FR-07.5: Todas as operações de exclusão devem utilizar `DELETE /api/{recurso}/{id}`.
- FR-07.6: Erros retornados pela API devem ser exibidos ao usuário de forma legível.

---

## Key Entities

| Entidade         | Campos Principais                                                                 | Relacionamentos                    |
|------------------|-----------------------------------------------------------------------------------|------------------------------------|
| Cliente          | id, nome, cpf, telefone, endereço, bairro, cidade, estado, cep                   | Possui várias Ordens de Serviço    |
| Veículo          | id, marca, modelo, ano_fabricacao, ano_modelo, placa, cor                         | Aparece em várias Ordens de Serviço |
| Ordem de Serviço | id, cliente_id, veiculo_id, data_entrada, data_saida, problema, observacoes       | Pertence a Cliente e Veículo; tem Serviços |
| Serviço          | id, ordem_servico_id, descricao, mecanico, data_hora_servico, data_hora_termino, valor | Pertence a Ordem de Serviço |

---

## Success Criteria

- SC-01: Um operador sem treinamento técnico consegue cadastrar um cliente, registrar um veículo e abrir uma ordem de serviço em menos de 5 minutos.
- SC-02: 100% das operações de CRUD (criar, listar, editar, excluir) funcionam para todos os 4 domínios sem erros.
- SC-03: Todas as telas carregam em menos de 2 segundos com conexão local ao backend.
- SC-04: Formulários com campos obrigatórios em branco não são submetidos e exibem mensagens de validação.
- SC-05: A navegação entre telas ocorre sem recarregamento completo da página (SPA behavior).
- SC-06: Erros do servidor são exibidos ao usuário com mensagem legível, não como tela em branco ou erro técnico.

---

## Assumptions

- O backend está rodando localmente na porta `9081` durante o desenvolvimento e testes.
- Não há autenticação nesta fase — o sistema é de acesso livre para operadores da oficina.
- As telas serão acessadas principalmente em desktop (telas largas), mas devem ser funcionais em telas menores.
- O soft-delete do backend (campo `ativo`) já está funcional nos endpoints — o frontend apenas chama `DELETE`.
- Os formulários de Ordem de Serviço carregam a lista de clientes e veículos ativos do backend para seleção.
- O sistema opera em português brasileiro.

---

## Dependencies

- Backend Spring Boot rodando na porta `9081` com endpoints ativos para: `/api/clientes`, `/api/veiculos`, `/api/servicos`, `/api/ordem-servicos`.
- Estrutura de módulos e componentes Angular já criada (clientes, veiculos, servicos, ordem-servico) — precisa ser preenchida com implementação funcional.
- `ApiService` existente em `core/services/api.ts` — será estendida com métodos `post`, `put`, `delete`.

---

## Out of Scope (Detailed)

- Login, logout, gerenciamento de usuários e perfis de acesso.
- Geração de PDF de ordens de serviço ou relatórios.
- Busca/filtro avançado nas listagens (além de listagem simples).
- Paginação server-side (paginação client-side é aceitável para volumes pequenos).
- Integração com ViaCEP ou APIs externas de consulta de CEP.
- Testes automatizados de componentes (além do que já existe em `.spec.ts` placeholder).
