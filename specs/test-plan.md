# Plano de Testes — Car-Repair

**Data:** 2026-05-07  
**Sistema:** Car-Repair (Spring Boot 3.4 + Angular 21)  
**Backend:** http://localhost:9081  
**Frontend:** http://localhost:4200

---

## Pré-requisitos

- [ ] Backend rodando (`./mvnw spring-boot:run`)
- [ ] Frontend rodando (`npm start` em `car-repair-frontend/`)
- [ ] Banco PostgreSQL acessível (`car_repair_db`)
- [ ] Migração de FK e colunas executada (ver seção Banco de Dados abaixo)

### Migrações de banco obrigatórias

```sql
-- 1. Corrigir FK da tabela servicos (aponta para tabela errada)
ALTER TABLE servicos DROP CONSTRAINT fk2bjeip0t0rt5uwebx9nfy1ull;
ALTER TABLE servicos ADD CONSTRAINT fk_servicos_ordem_servicos
  FOREIGN KEY (ordem_servico_id) REFERENCES ordem_servicos(id);

-- 2. Atualizar colunas de data para timestamp (suporte a hora)
ALTER TABLE servicos
  ALTER COLUMN data_hora_servico TYPE timestamp USING data_hora_servico::timestamp,
  ALTER COLUMN data_hora_termino TYPE timestamp USING data_hora_termino::timestamp;
```

---

## 1. Header e Navegação

| # | Ação | Resultado esperado |
|---|------|--------------------|
| H-01 | Abrir http://localhost:4200 | Logo da oficina visível no header |
| H-02 | Verificar ordem do menu | Sequência: **Clientes → Veículos → Ordens de Serviço → Serviços** |
| H-03 | Clicar em cada item do menu | Navega para a página correta sem erro |

---

## 2. Clientes

### 2.1 Listagem
| # | Ação | Resultado esperado |
|---|------|--------------------|
| C-01 | Acessar `/clientes` | Lista carrega (ou mensagem "Nenhum cliente cadastrado") |
| C-02 | Backend offline | Exibe mensagem de erro, não trava a tela |

### 2.2 Cadastro
| # | Ação | Resultado esperado |
|---|------|--------------------|
| C-03 | Clicar em "+ Novo Cliente" | Abre formulário vazio |
| C-04 | Submeter formulário vazio | Botão **Salvar** desabilitado (campos obrigatórios inválidos) |
| C-05 | Preencher CPF | Máscara aplica formato `000.000.000-00` automaticamente |
| C-06 | Preencher Telefone | Máscara aplica formato `(00) 00000-0000` |
| C-07 | Preencher CEP | Máscara aplica formato `00000-000` |
| C-08 | Preencher todos os campos obrigatórios e salvar | Redireciona para lista; cliente aparece na tabela |
| C-09 | Salvar cliente com CPF duplicado | Exibe mensagem de erro (409 do backend) |
| C-10 | Clicar em Cancelar | Retorna para lista sem salvar |

### 2.3 Edição
| # | Ação | Resultado esperado |
|---|------|--------------------|
| C-11 | Clicar em Editar em um cliente | Formulário pré-preenchido com dados do cliente; título "Editar Cliente" |
| C-12 | Alterar nome e salvar | Dados atualizados na lista |

### 2.4 Exclusão
| # | Ação | Resultado esperado |
|---|------|--------------------|
| C-13 | Clicar em Excluir | Confirmação solicitada via dialog |
| C-14 | Confirmar exclusão | Cliente desaparece da lista (soft-delete) |
| C-15 | Cancelar exclusão | Lista permanece inalterada |

---

## 3. Veículos

### 3.1 Cadastro
| # | Ação | Resultado esperado |
|---|------|--------------------|
| V-01 | Clicar em "+ Novo Veículo" | Formulário exibe todos os campos (marca, modelo, anos, placa, cor) |
| V-02 | Submeter vazio | Botão Salvar desabilitado |
| V-03 | Ano de Fabricação > ano atual | Erro inline: "Ano não pode ser maior que AAAA" |
| V-04 | Ano de Modelo > ano atual | Erro inline: "Ano não pode ser maior que AAAA" |
| V-05 | Ano = 0 ou negativo | Erro de validação |
| V-06 | Preencher placa com letras maiúsculas/minúsculas | Salva normalmente |
| V-07 | Preencher todos os campos válidos e salvar | Redireciona para lista; veículo aparece |
| V-08 | Editar veículo | Campos pré-preenchidos corretamente |

---

## 4. Ordens de Serviço

### 4.1 Cadastro
| # | Ação | Resultado esperado |
|---|------|--------------------|
| O-01 | Clicar em "+ Nova Ordem de Serviço" | Dropdowns de Cliente e Veículo carregados |
| O-02 | Submeter sem cliente | Botão Salvar desabilitado |
| O-03 | Data de Saída anterior à Data de Entrada | Borda vermelha + mensagem "Data de saída não pode ser anterior..." |
| O-04 | Data de Saída inválida | Botão Salvar desabilitado |
| O-05 | Preencher apenas campos obrigatórios (sem saída/observações) e salvar | Salva com sucesso — campos opcionais aceitos como nulos |
| O-06 | Preencher todos os campos e salvar | Redireciona para lista; OS aparece com cliente e veículo |
| O-07 | Verificar lista de OS | Mostra cliente, veículo, data de entrada, status |

### 4.2 Edição
| # | Ação | Resultado esperado |
|---|------|--------------------|
| O-08 | Editar OS existente | Cliente e veículo pré-selecionados no dropdown |
| O-09 | Alterar problema e salvar | Dados atualizados |

---

## 5. Serviços

### 5.1 Cadastro — campos e máscara de valor
| # | Ação | Resultado esperado |
|---|------|--------------------|
| S-01 | Clicar em "+ Novo Serviço" | Formulário exibe todos os campos |
| S-02 | Campo **Valor**: digitar `100` | Exibe `R$ 1,00` |
| S-03 | Campo **Valor**: digitar `10000` | Exibe `R$ 100,00` |
| S-04 | Campo **Valor**: digitar `1500000` | Exibe `R$ 15.000,00` |
| S-05 | Campo **Valor**: apagar tudo | Campo vazio; botão Salvar desabilitado (required) |
| S-06 | Campo **Data/Hora de Início** | Abre seletor de data E hora |
| S-07 | Campo **Data/Hora de Término** | Abre seletor de data E hora |
| S-08 | Término anterior ao Início | Borda vermelha + mensagem de erro |
| S-09 | Término inválido | Botão Salvar desabilitado |

### 5.2 Cadastro — com e sem Ordem de Serviço
| # | Ação | Resultado esperado |
|---|------|--------------------|
| S-10 | Salvar serviço **sem** Ordem de Serviço | Salva com sucesso; `ordemServicoId` = null |
| S-11 | Salvar serviço **com** Ordem de Serviço selecionada | Salva com sucesso, sem erro 500 |
| S-12 | Verificar serviço salvo na lista | Mostra descrição, mecânico, início, término e valor formatado (R$) |

### 5.3 Edição
| # | Ação | Resultado esperado |
|---|------|--------------------|
| S-13 | Editar serviço existente | Campo Valor pré-preenchido com `R$ X,XX` |
| S-14 | Campo Valor no edit de `R$ 150,00` | Exibe `R$ 150,00` (não `R$ 1,50`) |
| S-15 | Alterar valor e salvar | Valor atualizado no banco |

---

## 6. Testes de Regressão Geral

| # | Ação | Resultado esperado |
|---|------|--------------------|
| R-01 | Salvar qualquer formulário | Botão "Salvar" não fica travado em "Salvando..." após resposta do backend |
| R-02 | Erros 400/422/500 do backend | Mensagem de erro aparece na tela, formulário liberado |
| R-03 | Navegar entre páginas e voltar | Dados não ficam de uma tela para outra |
| R-04 | Recarregar página em qualquer rota | Não exibe tela branca (SSR/hydration ok) |

---

## 7. Testes de API (via Swagger)

Acessar: http://localhost:9081/swagger-ui/index.html

| # | Endpoint | Verificar |
|---|----------|-----------|
| A-01 | `POST /clientes` | Retorna 201 com `id` UUID |
| A-02 | `GET /clientes` | Retorna paginação (`content`, `totalElements`) |
| A-03 | `PUT /clientes/{id}` | Atualiza e retorna 200 |
| A-04 | `DELETE /clientes/{id}` | Retorna 200; registro com `ativo=false` no banco |
| A-05 | `POST /ordem-servicos` com `clienteId` inválido | Retorna 422 |
| A-06 | `POST /servicos` com `ordemServicoId` válido | Retorna 201 sem erro 500 |
| A-07 | `POST /servicos` com `ordemServicoId` inválido | Retorna 422 (FK violation) |
| A-08 | `GET /servicos/{id}` | Retorna datas no formato `"yyyy-MM-dd'T'HH:mm"` |

---

## 8. Checklist de Bugs Corrigidos (verificação regrssiva)

| Bug | Como reproduzir | Status esperado |
|-----|----------------|-----------------|
| Botão "Salvando..." travado | Submeter qualquer form e aguardar resposta | **CORRIGIDO** — libera após resposta |
| Campos do form de veículo não aparecem | Acessar `/veiculos/novo` | **CORRIGIDO** — campos visíveis |
| Erro 400 ao salvar veículo | Submeter form de veículo com campos válidos | **CORRIGIDO** — salva com sucesso |
| Erro 500 ao salvar serviço com OS | Selecionar uma OS no form de serviço e salvar | **CORRIGIDO** — salva com sucesso |
| Data de saída/observações obrigatórias na OS | Criar OS sem data de saída | **CORRIGIDO** — campos opcionais |
| FK errada (`ordem_servico` vs `ordem_servicos`) | Salvar serviço com OS após migração SQL | **CORRIGIDO** após rodar migration |
