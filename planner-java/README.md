# NLW Journey - Java

## Tecnologias

- Java 21
- Spring Framework / Spring Boot (3.3.1)
- H2 Database

### Bibliotecas

- spring-boot-starter-web
- spring-boot-starter-data-jpa
- spring-boot-devtools
- flyway-core
- h2
- lombok

## Links

[Spring Initializr](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.3.1&packaging=jar&jvmVersion=21&groupId=br.com.rocketseat.hiokdev&artifactId=planner-java&name=planner-java&description=Planner%20-%20Backend%20Java&packageName=br.com.rocketseat.hiokdev.planner-java&dependencies=web,flyway,devtools,lombok,data-jpa,h2)

## Diferenças em relação ao evento

- Separação do projeto em camada de domain e camada de api (refatoração)
- Criação da camada de trip service (refatoração)
- Criação do DTO TripData para não retornar diretamente o modelo de domínio (refatoração)
- Remoção da camada de domínio as dependências da camada de api no ParticipantService (refatoração)
- Remoção da camada de domínio as dependências da camada de api no ActivityService (refatoração)
- Remoção da camada de domínio as dependências da camada de api no LinkService (refatoração)
- Movido lógica de negócio da camada de api para a camada de domínio no confirm participant endpoint (refatoração)
- Separação trip service em trip query service e trip service (refatoração)
- Ajustado os formatos de respostas das requisições para o frontend e mobile (fix)
- Adicionado api exception handler (novo recurso)
- Adicionado validação para TripCreateRequestPayload
- 

## Feature extras

- Adicionar validações nos campos de dados:

    - Data de começo de viagem é depois da data atual
    - Data de final da viagem é depois da data de começo
    - Data da atividade está entre as datas da viagem

- Mapeamento das exceções da aplicação

    - Tratativa de erros personalizadas (parcial)

- Envio de e-mails para um servidor smtp em docker
