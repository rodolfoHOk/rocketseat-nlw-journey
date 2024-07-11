# NLW Journey - Go

## Tecnologias

- Go Lang
- Docker
- PostgreSQL
- MailPit
- Rest

### Bibliotecas adicionais

- github.com/jackc/pgx/v5
- github.com/go-chi/chi/middleware
- github.com/go-chi/chi/v5
- github.com/google/uuid
- go.uber.org/zap
- github.com/go-playground/validator/v10
- github.com/phenpessoa/gutils/netutils/httputils
- github.com/wneessen/go-mail

### Bibliotecas cli

- github.com/discord-gophers/goapi-gen
- github.com/jackc/tern/v2@latest
- github.com/sqlc-dev/sqlc

## Guia

### go init

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

- criar sqlc.yaml
- sqlc generate -f ./internal/pgstore/sqlc.yaml
- go mod tidy

### go gen

- criar gen.go
- go generate ./...

### go run

- go run ./cmd/journey/journey.go

### vscode with go extension

- ctrl + shift + p -> Go: generate interface stubs -> api API spec.ServerInterface

## Links

- [Github: goapi-gen](https://github.com/discord-gophers/goapi-gen)
- [Github: sqlc](https://github.com/sqlc-dev/sqlc)
- [Github: goqu](https://github.com/doug-martin/goqu)
- [Github: squirrel](https://github.com/Masterminds/squirrel)
- [Github: pgx](https://github.com/jackc/pgx)
- [Github: uuid](https://github.com/google/uuid)
- [Github: tern](https://github.com/jackc/tern)
- [Github: validator](https://github.com/go-playground/validator)
- [Github: go-mail](https://github.com/wneessen/go-mail)
