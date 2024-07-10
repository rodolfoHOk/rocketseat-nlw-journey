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

- go install github.com/discord-gophers/goapi-gen@latest
- export GOPATH=$HOME/go
- export PATH=$PATH:$GOROOT/bin:$GOPATH/bin
- goapi-gen --out ./internal/api/spec/journey.gen.spec.go ./internal/api/spec/journey.spec.json
- go mod tidy
- go get -u ./...

## Links

- [Github: goapi-gen](https://github.com/discord-gophers/goapi-gen)
