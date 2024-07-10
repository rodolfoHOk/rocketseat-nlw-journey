# NLW Journey - Go

## Tecnologias

- Go Lang
- Docker
- PostgreSQL
- MailPit

### Bibliotecas adicionais

- github.com/discord-gophers/goapi-gen
- github.com/go-chi/chi/v5

## Guia

### Iniciar projeto Go

- go mod init github.com/rodolfoHOk/rocketseat-nlw-journey/planner-go

### goapi-gen

- go install github.com/discord-gophers/goapi-gen@latest
- goapi-gen --package=spec --out ./internal/api/spec/journey.gen.spec.go ./internal/api/spec/journey.spec.json
- go mod tidy
- go get -u ./...

### tern

- go install github.com/jackc/tern/v2@latest
- tern init ./internal/pgstore/migrations
- tern new --migrations ./internal/pgstore/migrations create_trips_table
- tern new --migrations ./internal/pgstore/migrations create_participants_table
- tern new --migrations ./internal/pgstore/migrations create_activities_table
- tern new --migrations ./internal/pgstore/migrations create_links_table
- tern migrate --migrations ./internal/pgstore/migrations --config ./internal/pgstore/migrations/tern.conf

### sqlc

-

## Links

- [Github: goapi-gen](https://github.com/discord-gophers/goapi-gen)
- [Github: sqlc](https://github.com/sqlc-dev/sqlc)
- [Github: goqu](https://github.com/doug-martin/goqu)
- [Github: squirrel](https://github.com/Masterminds/squirrel)
- [Github: pgx](https://github.com/jackc/pgx)
- [Github: uuid](https://github.com/google/uuid)
- [Github: tern](https://github.com/jackc/tern)
