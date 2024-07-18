# NLW Journey - Java

## Tecnologias

- Java 21
- Spring Framework / Spring Boot (3.3.1)
- H2 Database

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
- todo: use mail gateway in domain services
