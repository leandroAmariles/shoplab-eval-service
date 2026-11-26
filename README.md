# shoplab-eval-service

Servicio de historial de ejecuciones de evaluación RAG y consola `admin-eval` de ShopLab Helpdesk (LAB-130, ADR-0035).

## Responsabilidades

- Persistencia inmutable del historial de corridas de evaluación (`POST /api/eval/runs`, `GET /api/eval/runs/{runId}`) protegida por `X-Eval-Token`.
- Lectura de la configuración activa del asistente (`GET /api/eval/settings`) desde `chat-service` (`GET /api/assistant/settings`) mediante la interfaz HTTP reactiva generada por `shoplab-contracts`.
- Consultas históricas y comparación de ejecuciones para la pantalla `/admin-eval` (`GET /api/eval/history/runs`, `/runs/{runId}`, `/compare`).
- Base de datos PostgreSQL propia (`eval`, esquema `eval`, migraciones `db/migration/eval`).

## Construcción y verificación

```bash
GITHUB_TOKEN=... ./mvnw -B -ntp -s .mvn/ci-settings.xml verify
GITHUB_TOKEN=... docker build --secret id=github_token,env=GITHUB_TOKEN -t eval-service .
```
