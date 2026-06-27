# SmartWarehouse

Simulação de um galpão industrial inteligente criado para a disciplina de Projeto de Software, com o intuito de explorar padrões de projeto. 

## Como rodar com Docker

Pré-requisito: Docker com Docker Compose. Na raiz do projeto, execute:

```bash
docker compose up --build -d
```

Em seguida, acesse **http://localhost:8080**.

Para parar:

```bash
docker compose down
```

## Endpoints

### Endpoints do Inventário

- **`GET   /api/inventario/mapa`**: retorna o estado de cada zona.
- **`GET   /api/inventario/auditoria`**: retorna todos os produtos no galpão.
- **`POST  /api/inventario/sensores/leitura`**: registra uma leitura em um sensor.
- **`POST  /api/inventario/zonas/{id}/desbloquear`**: desbloqueia a zona.

### Endpoints de Operação

- **`GET   /api/operacoes/estrategias`**: lista as estratégias de alocação disponíveis.
- **`GET   /api/operacoes/robos`**: lista os robôs com seus estados.
- **`POST  /api/operacoes/recebimento`**: aloca produtos conforme a estratégia escolhida.
