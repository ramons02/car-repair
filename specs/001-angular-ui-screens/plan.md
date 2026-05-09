# Plano de Implementação: Telas Angular Car-Repair

**Feature**: 001-angular-ui-screens  
**Spec**: [spec.md](./spec.md)  
**Research**: [research.md](./research.md)  
**Data Model**: [data-model.md](./data-model.md)  
**Branch**: main  
**Date**: 2026-05-07  

---

## Contexto Técnico

| Item | Estado Atual | Ação Necessária |
|------|-------------|-----------------|
| `ApiService` | Só tem `get()`, URL errada (8080) | Corrigir URL + adicionar `post`, `put`, `delete` |
| `app.config.ts` | Sem `provideHttpClient()` | Adicionar provider |
| `app.routes.ts` | Vazio | Configurar todas as rotas lazy |
| `app.html` | Template padrão Angular | Substituir por layout com header + router-outlet |
| `HeaderComponent` | Placeholder `<p>` | Implementar menu de navegação |
| Todos os List/Form | Placeholder `<p>` | Implementar completamente |
| Models | Tipos incorretos (Date/number onde deveria ser string/number) | Corrigir interfaces |

---

## Fases de Implementação

### FASE 1 — Infraestrutura (Pré-requisito para tudo)

Estas tarefas desbloqueiam todo o restante. Devem ser feitas na ordem.

---

#### T-01: Corrigir ApiService
**Arquivo**: `car-repair-frontend/src/app/core/services/api.ts`

Mudanças:
1. Corrigir `API_URL` de `http://localhost:8080/api` para `http://localhost:9081/api`
2. Adicionar método `post<T>(endpoint, body)`
3. Adicionar método `put<T>(endpoint, body)`
4. Adicionar método `delete<T>(endpoint)`

```typescript
// Resultado esperado:
post<T>(endpoint: string, body: unknown): Observable<T>
put<T>(endpoint: string, body: unknown): Observable<T>
delete<T>(endpoint: string): Observable<T>
```

**Critério de aceitação**: Todos os 4 métodos (get, post, put, delete) chamam o URL correto e retornam `Observable<T>`.

---

#### T-02: Adicionar provideHttpClient ao appConfig
**Arquivo**: `car-repair-frontend/src/app/app.config.ts`

Mudança: importar `provideHttpClient` de `@angular/common/http` e adicioná-lo ao array de `providers`.

**Critério de aceitação**: Aplicação inicializa sem erro `NullInjectorError: No provider for HttpClient`.

---

#### T-03: Corrigir interfaces dos Models
**Arquivos**:
- `src/app/models/veiculo.model.ts` — `ano_fabricacao` e `ano_modelo`: `Date` → `number`
- `src/app/models/ordem-servico.model.ts` — `cliente_id`, `veiculo_id`: `number` → `string`; adicionar campo `id?: string`
- `src/app/models/cliente.model.ts` — adicionar `id?: string`
- `src/app/models/servico.model.ts` — `ordem_servico_id`: `number` → `string`; adicionar `id?: string`

**Critério de aceitação**: Nenhum erro de tipagem nos componentes ao usar os models.

---

#### T-04: Substituir app.html pelo layout da aplicação
**Arquivo**: `car-repair-frontend/src/app/app.html`

Substituir todo o conteúdo pelo layout base:
```html
<app-header></app-header>
<main class="container mx-auto p-4">
  <router-outlet></router-outlet>
</main>
```

**Arquivo**: `car-repair-frontend/src/app/app.ts`
- Remover o `signal('car-repair-frontend')` e propriedade `title`
- Importar `HeaderComponent` e `RouterOutlet` no array `imports` do componente

**Critério de aceitação**: Aplicação renderiza o header e o conteúdo da rota ativa.

---

#### T-05: Implementar HeaderComponent
**Arquivos**:
- `src/app/shared/components/header/header.ts`
- `src/app/shared/components/header/header.html`
- `src/app/shared/components/header/header.css`

Implementação:
- Barra de navegação com nome do sistema ("Car-Repair") e links para:
  - Ordens de Serviço → `/ordens-servico`
  - Clientes → `/clientes`
  - Veículos → `/veiculos`
  - Serviços → `/servicos`
- Usar `RouterLink` e `RouterLinkActive` para destacar o item da rota ativa
- Estilo Tailwind CSS (fundo escuro, links horizontais)

**Critério de aceitação**: Header visível em todas as páginas; link da rota ativa destacado visualmente.

---

#### T-06: Configurar rotas em app.routes.ts
**Arquivo**: `car-repair-frontend/src/app/app.routes.ts`

```typescript
export const routes: Routes = [
  { path: '', redirectTo: 'ordens-servico', pathMatch: 'full' },
  {
    path: 'clientes',
    loadComponent: () => import('./features/clientes/cliente-list/cliente-list').then(m => m.ClienteList)
  },
  {
    path: 'clientes/novo',
    loadComponent: () => import('./features/clientes/cliente-form/cliente-form').then(m => m.ClienteForm)
  },
  {
    path: 'clientes/:id/editar',
    loadComponent: () => import('./features/clientes/cliente-form/cliente-form').then(m => m.ClienteForm)
  },
  // ... mesmo padrão para veiculos, servicos, ordens-servico
  { path: '**', redirectTo: 'ordens-servico' }
];
```

**Critério de aceitação**: Navegando para `/clientes`, `/veiculos`, `/servicos`, `/ordens-servico` renderiza o componente correto.

---

### FASE 2 — Tela de Clientes

Depende de: T-01, T-02, T-03, T-05, T-06

---

#### T-07: Implementar ClienteList
**Arquivos**:
- `src/app/features/clientes/cliente-list/cliente-list.ts`
- `src/app/features/clientes/cliente-list/cliente-list.html`

Funcionalidades:
- Injetar `ApiService` e carregar `GET /clientes` no `ngOnInit`
- Exibir tabela com colunas: Nome, CPF, Telefone, Cidade
- Botão "Novo Cliente" → navegar para `/clientes/novo`
- Botão "Editar" por linha → navegar para `/clientes/:id/editar`
- Botão "Excluir" por linha → confirmar com `window.confirm()` → chamar `DELETE /clientes/:id` → recarregar lista
- Exibir mensagem de carregamento enquanto busca
- Exibir mensagem de erro se a requisição falhar

**Critério de aceitação**: Lista clientes do backend; excluir remove da lista; botões de editar e novo navegam corretamente.

---

#### T-08: Implementar ClienteForm
**Arquivos**:
- `src/app/features/clientes/cliente-form/cliente-form.ts`
- `src/app/features/clientes/cliente-form/cliente-form.html`

Funcionalidades:
- Detectar se é modo "novo" (sem `:id` na rota) ou "editar" (com `:id`)
- No modo "editar": carregar dados do cliente via `GET /clientes/:id` e pré-preencher formulário
- Campos: Nome, CPF, Telefone, Endereço, Bairro, Cidade, Estado, CEP (todos obrigatórios)
- Validação: campos obrigatórios não podem ser vazios
- Botão "Salvar": `POST /clientes` (novo) ou `PUT /clientes/:id` (editar)
- Botão "Cancelar": navegar de volta para `/clientes`
- Exibir erro da API se CPF duplicado (status 409)

**Critério de aceitação**: Criar e editar cliente persiste no backend; validação impede submissão de campos vazios.

---

### FASE 3 — Tela de Veículos

Depende de: T-01, T-02, T-03, T-05, T-06

---

#### T-09: Implementar VeiculoList
**Arquivos**:
- `src/app/features/veiculos/veiculo-list/veiculo-list.ts`
- `src/app/features/veiculos/veiculo-list/veiculo-list.html`

Funcionalidades (mesmo padrão de ClienteList):
- Carregar `GET /veiculos`
- Tabela com colunas: Placa, Marca, Modelo, Ano Fabricação, Cor
- Botões: Novo, Editar, Excluir

**Critério de aceitação**: Lista, edita e exclui veículos corretamente.

---

#### T-10: Implementar VeiculoForm
**Arquivos**:
- `src/app/features/veiculos/veiculo-form/veiculo-form.ts`
- `src/app/features/veiculos/veiculo-form/veiculo-form.html`

Funcionalidades (mesmo padrão de ClienteForm):
- Campos: Marca, Modelo, Ano Fabricação (number), Ano Modelo (number), Placa, Cor
- Validação de campos obrigatórios
- Erro 409 para placa duplicada

**Critério de aceitação**: Criar e editar veículo persiste corretamente.

---

### FASE 4 — Tela de Serviços

Depende de: T-01, T-02, T-03, T-05, T-06

---

#### T-11: Implementar ServicoList
**Arquivos**:
- `src/app/features/servicos/servico-list/servico-list.ts`
- `src/app/features/servicos/servico-list/servico-list.html`

Funcionalidades:
- Carregar `GET /servicos`
- Tabela com colunas: Descrição, Mecânico, Início, Término, Valor
- Botões: Novo, Editar, Excluir

**Critério de aceitação**: Lista, edita e exclui serviços corretamente.

---

#### T-12: Implementar ServicoForm
**Arquivos**:
- `src/app/features/servicos/servico-form/servico-form.ts`
- `src/app/features/servicos/servico-form/servico-form.html`

Funcionalidades:
- Campos: Descrição, Mecânico, Data/Hora Início (datetime-local), Data/Hora Término (datetime-local), Valor (number), Ordem de Serviço (opcional, select)
- Validação: campos obrigatórios e que término >= início

**Critério de aceitação**: Criar e editar serviço funciona; campo de OS é opcional.

---

### FASE 5 — Tela de Ordens de Serviço

Depende de: T-01, T-02, T-03, T-05, T-06 (e idealmente T-07/T-09 já funcionando para testar seleção)

---

#### T-13: Implementar OrdemServicoList
**Arquivos**:
- `src/app/features/ordem-servico/ordem-servico-list/ordem-servico-list.ts`
- `src/app/features/ordem-servico/ordem-servico-list/ordem-servico-list.html`

Funcionalidades:
- Carregar `GET /ordem-servicos`
- Tabela com colunas: ID, Cliente, Veículo (placa), Problema, Data Entrada, Data Saída
- Botões: Nova OS, Editar, Excluir

**Critério de aceitação**: Lista ordens de serviço do backend corretamente.

---

#### T-14: Implementar OrdemServicoForm
**Arquivos**:
- `src/app/features/ordem-servico/ordem-servico-form/ordem-servico-form.ts`
- `src/app/features/ordem-servico/ordem-servico-form/ordem-servico-form.html`

Funcionalidades:
- No `ngOnInit`: carregar lista de clientes (`GET /clientes`) e veículos (`GET /veiculos`) para os selects
- Campos: Cliente (select), Veículo (select), Data Entrada (date), Data Saída (date, opcional), Problema (textarea), Observações (textarea, opcional)
- Validação: cliente, veículo, data entrada e problema são obrigatórios

**Critério de aceitação**: Criar OS com cliente e veículo selecionados persiste no backend; selects carregam dados reais.

---

## Ordem de Execução Recomendada

```
T-01 → T-02 → T-03 → T-04 → T-05 → T-06
                                        ↓
                              T-07 → T-08 (Clientes)
                              T-09 → T-10 (Veículos)
                              T-11 → T-12 (Serviços)
                              T-13 → T-14 (Ordens)
```

As tarefas de FASE 2, 3, 4 e 5 são independentes entre si e podem ser feitas em paralelo após a FASE 1 estar completa.

---

## Arquivos a Modificar por Tarefa

| Tarefa | Arquivo(s) Principal(is) |
|--------|--------------------------|
| T-01 | `core/services/api.ts` |
| T-02 | `app.config.ts` |
| T-03 | `models/*.model.ts` (4 arquivos) |
| T-04 | `app.html`, `app.ts` |
| T-05 | `shared/components/header/header.{ts,html,css}` |
| T-06 | `app.routes.ts` |
| T-07 | `features/clientes/cliente-list/cliente-list.{ts,html}` |
| T-08 | `features/clientes/cliente-form/cliente-form.{ts,html}` |
| T-09 | `features/veiculos/veiculo-list/veiculo-list.{ts,html}` |
| T-10 | `features/veiculos/veiculo-form/veiculo-form.{ts,html}` |
| T-11 | `features/servicos/servico-list/servico-list.{ts,html}` |
| T-12 | `features/servicos/servico-form/servico-form.{ts,html}` |
| T-13 | `features/ordem-servico/ordem-servico-list/ordem-servico-list.{ts,html}` |
| T-14 | `features/ordem-servico/ordem-servico-form/ordem-servico-form.{ts,html}` |

---

## Critérios de Conclusão da Feature

- [x] T-01 a T-06 completos (infraestrutura)
- [x] T-07 a T-08 completos (Clientes CRUD funcional)
- [x] T-09 a T-10 completos (Veículos CRUD funcional)
- [x] T-11 a T-12 completos (Serviços CRUD funcional)
- [x] T-13 a T-14 completos (Ordens de Serviço CRUD funcional)
- [ ] Aplicação inicia sem erros no console
- [ ] Navegação entre todas as telas funciona sem recarregar a página
- [ ] Erros do backend exibidos ao usuário de forma legível
