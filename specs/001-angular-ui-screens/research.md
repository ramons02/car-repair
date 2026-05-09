# Research: Telas Angular Car-Repair

**Feature**: 001-angular-ui-screens  
**Date**: 2026-05-07  

---

## Findings from Codebase Audit

### Decision 1: HTTP Client não está provisionado
**Observação**: `app.config.ts` não inclui `provideHttpClient()`. Sem isso, a injeção de `HttpClient` em `ApiService` vai falhar em runtime.  
**Decisão**: Adicionar `provideHttpClient()` em `app.config.ts`.  
**Alternativas consideradas**: Usar `HttpClientModule` via NgModule — descartado pois o projeto usa standalone config.

### Decision 2: Componentes são standalone mas modules usam NgModule
**Observação**: Cada componente usa `imports: []` no `@Component` (padrão standalone), mas os feature modules usam `@NgModule`. Em Angular 21, o padrão recomendado é rotas standalone com lazy loading direto.  
**Decisão**: Manter os NgModules existentes, mas não declarar os componentes neles. Configurar rotas lazy em `app.routes.ts` carregando os componentes standalone diretamente via `loadComponent`.  
**Alternativas consideradas**: Declarar componentes nos NgModules — descartado pois conflita com o padrão standalone.

### Decision 3: Estrutura de rotas
**Observação**: `app.routes.ts` está vazio. Os routing modules dos features também têm rotas vazias.  
**Decisão**: Configurar todas as rotas em `app.routes.ts` usando `loadComponent` para lazy loading. Os routing modules de cada feature não são mais necessários mas serão preservados sem uso para não introduzir breaking changes desnecessários.

### Decision 4: ApiService — métodos faltando
**Observação**: `ApiService` só tem `get()`. Todos os formulários precisarão de `post()`, `put()`, `delete()`.  
**Decisão**: Adicionar os três métodos ao serviço existente e corrigir `API_URL` de `8080` para `9081`.

### Decision 5: Layout da aplicação
**Observação**: `app.html` é o template padrão do Angular ("Hello, title"). `<router-outlet>` está no final mas a estrutura de layout precisa ser refeita.  
**Decisão**: Substituir `app.html` por layout simples: `<app-header>` + `<main><router-outlet></main>`. Remover todo o CSS inline de placeholder.

### Decision 6: Confirmação de exclusão
**Observação**: Spec define "com confirmação antes de excluir". Não há biblioteca de modal instalada.  
**Decisão**: Usar `window.confirm()` nativo para simplicidade. Não introduzir dependências novas.

### Decision 7: Seleção de cliente/veículo na Ordem de Serviço
**Observação**: O formulário de OS precisa listar clientes e veículos do backend.  
**Decisão**: Ao abrir o form de OS, fazer dois `GET` paralelos para `/api/clientes` e `/api/veiculos` e popular selects.

---

## Endpoints Backend (confirmados via CLAUDE.md)

| Recurso            | Base URL                    |
|--------------------|-----------------------------|
| Clientes           | `GET/POST/PUT/DELETE /api/clientes/{id}` |
| Veículos           | `GET/POST/PUT/DELETE /api/veiculos/{id}` |
| Serviços           | `GET/POST/PUT/DELETE /api/servicos/{id}` |
| Ordens de Serviço  | `GET/POST/PUT/DELETE /api/ordem-servicos/{id}` |

Todos os endpoints seguem o `GenericController` com padrão: `GET /` (lista), `GET /{id}`, `POST /`, `PUT /{id}`, `DELETE /{id}`.
