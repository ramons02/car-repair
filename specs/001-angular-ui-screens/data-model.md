# Data Model: Telas Angular Car-Repair

**Feature**: 001-angular-ui-screens  
**Date**: 2026-05-07  

---

## Entidades e Interfaces TypeScript

### Cliente (`src/app/models/cliente.model.ts`)
```
id?          : string (UUID)
nome         : string  [obrigatório]
cpf          : string  [obrigatório, único]
telefone     : string  [obrigatório]
endereco     : string  [obrigatório]
bairro       : string  [obrigatório]
cidade       : string  [obrigatório]
estado       : string  [obrigatório]
cep          : string  [obrigatório]
ativo?       : boolean (gerenciado pelo backend)
```

### Veiculo (`src/app/models/veiculo.model.ts`)
```
id?              : string (UUID)
marca            : string  [obrigatório]
modelo           : string  [obrigatório]
ano_fabricacao   : number  [obrigatório]
ano_modelo       : number  [obrigatório]
placa            : string  [obrigatório, único]
cor              : string  [obrigatório]
ativo?           : boolean
```
> Nota: o modelo atual usa `Date` para anos — deve ser corrigido para `number`.

### OrdemServico (`src/app/models/ordem-servico.model.ts`)
```
id?             : string (UUID)
cliente_id      : string  [obrigatório]
veiculo_id      : string  [obrigatório]
data_entrada    : string  [obrigatório, ISO date]
data_saida?     : string  [opcional]
problema        : string  [obrigatório]
observacoes?    : string  [opcional]
ativo?          : boolean
```
> Nota: o modelo atual usa `number` para IDs — deve ser corrigido para `string` (UUID).

### Servico (`src/app/models/servico.model.ts`)
```
id?                  : string (UUID)
ordem_servico_id?    : string  [opcional]
descricao            : string  [obrigatório]
mecanico             : string  [obrigatório]
data_hora_servico    : string  [obrigatório, ISO datetime]
data_hora_termino    : string  [obrigatório, ISO datetime]
valor                : number  [obrigatório]
ativo?               : boolean
```

---

## Relacionamentos

```
Cliente ──< OrdemServico >── Veiculo
OrdemServico ──< Servico
```

- Um cliente pode ter muitas ordens de serviço.
- Um veículo pode aparecer em muitas ordens de serviço.
- Uma ordem de serviço tem muitos serviços realizados.

---

## Validações do Frontend

| Campo | Regra |
|-------|-------|
| CPF | Obrigatório, 11 dígitos (pode usar máscara) |
| Placa | Obrigatória, formato `AAA-0000` ou Mercosul `AAA0A00` |
| Valor | Obrigatório, número positivo |
| Datas | Data de saída não pode ser anterior à data de entrada |
| IDs relacionais | Cliente e veículo obrigatórios na OS |
