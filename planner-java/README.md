# NLW Journey - Java

## Tecnologias

- Java 21
- Spring Framework / Spring Boot (3.3.1)
- H2 Database
- OpenApi / Swagger UI
- Unit Tests
- Integration Tests

### Bibliotecas

- spring-boot-starter-web
- spring-boot-starter-data-jpa
- spring-boot-starter-validation
- spring-boot-starter-freemarker
- spring-boot-starter-mail
- spring-boot-devtools
- flyway-core
- h2
- lombok
- springdoc-openapi-starter-webmvc-ui
- greenmail-junit5
- jacoco-maven-plugin

## Melhorias

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
- Adicionado validação para o endpoint de create trip
- Adicionado validação para o endpoint de update trip
- Adicionado validação para o endpoint de confirm participant
- Adicionado validação para o endpoint de invite participant
- Adicionado validação para o endpoint de register activity
- Adicionado validação para o endpoint de register link
- Adicionado SMTP mail service
- Adicionado confirm trip email template
- Adicionado confirm presence email template
- Adicionado método trigger confirmation email to trip owner no trip service
- Implementado o método trigger confirmation email to participant no participant service
- Implementado o método trigger confirmation email to participants no participant service
- Adicionado dependência do spring doc e configuração do spring doc
- Adicionado documentação open api para trip controller
- Adicionado documentação open api para problem response dtos
- Adicionado documentação open api para trip dtos
- Adicionado documentação open api para participant dtos
- Adicionado documentação open api para activity dtos
- Adicionado documentação open api para link dtos
- Adicionado testes para trip query service
- Adicionado testes para trip service
- Adicionado testes para participant service
- Adicionado testes para activity service
- Adicionado testes para link service
- Adicionado testes de integração para smtp mail service
- Adicionado testes para trip controller
- Adicionado testes para participant controller
- Adicionado testes para api exception handler

## Imagens

<img src="https://raw.githubusercontent.com/rodolfoHOk/portfolio-img/main/images/nlw-journey-java-01.png" alt="NLW Journey Java API 01" width="600" />
<img src="https://raw.githubusercontent.com/rodolfoHOk/portfolio-img/main/images/nlw-journey-java-02.png" alt="NLW Journey Java API 02" width="600" />

<img src="https://raw.githubusercontent.com/rodolfoHOk/portfolio-img/main/images/nlw-journey-java-03.png" alt="NLW Journey Java API 03" width="600" />
<img src="https://raw.githubusercontent.com/rodolfoHOk/portfolio-img/main/images/nlw-journey-java-04.png" alt="NLW Journey Java API 04" width="600" />

<img src="https://raw.githubusercontent.com/rodolfoHOk/portfolio-img/main/images/nlw-journey-java-05.png" alt="NLW Journey Java API 05" width="600" />
<img src="https://raw.githubusercontent.com/rodolfoHOk/portfolio-img/main/images/nlw-journey-java-06.png" alt="NLW Journey Java API 06" width="600" />

## Rodar

### Requisitos

- Java 21
- Docker

### Comandos

- dentro da pasta: planner-java
- docker compose up -d
- ./mvnw package
- java -jar target/planner-java-1.0.0.jar
- no navegador web preferido: http://localhost:8080/swagger-ui/index.html

## Links

[Spring Initializr](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.3.1&packaging=jar&jvmVersion=21&groupId=br.com.rocketseat.hiokdev&artifactId=planner-java&name=planner-java&description=Planner%20-%20Backend%20Java&packageName=br.com.rocketseat.hiokdev.planner-java&dependencies=web,flyway,devtools,lombok,data-jpa,h2)
