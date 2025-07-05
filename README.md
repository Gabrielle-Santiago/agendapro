# Agenda Pro
## 📌 Conceito

A aplicação tem como objetivo **auxiliar no agendamento automatizado de consultas** para a área da saúde. O sistema permite que **pacientes agendem horários com profissionais disponíveis**, considerando regras específicas como tempo de duração da consulta, intervalo entre atendimentos e horário comercial. A aplicação também oferece:

* Um **chat em tempo real** entre usuários;
* Integração com a **agenda pessoal do Google Calendar**, permitindo o registro automático de compromissos.

---

## ⚙️ Tecnologias Utilizadas

* **Spring Boot**
* **Spring Security**
* **PostgreSQL**
* **Google Calendar API**
* **WebSocket (STOMP protocol)**
* **MapStruct**

---

## 🗃️ Organização

### 📌 Banco de Dados

A aplicação utiliza o banco de dados **PostgreSQL** para armazenar informações relacionadas a usuários, consultas e autenticação. Foi adotada a estratégia `@Inheritance(strategy = InheritanceType.SINGLE_TABLE)`, armazenando todas as entidades de usuários (pacientes e funcionários) em uma única tabela.

#### Verificação via pgAdmin

```sql
SELECT * FROM users;
```

### Configuração - `application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/name-database
spring.datasource.username=USER
spring.datasource.password=PASSWORD
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update  # Em produção, utilize outra configuração
spring.jpa.show-sql=true
```

---

## 🔄 Mapper

A aplicação utiliza o **MapStruct** para realizar mapeamentos entre DTOs e entidades. Uma das principais responsabilidades dos mappers é:

* **Criptografar senhas** de pacientes e funcionários no momento do cadastro, garantindo a segurança dos dados.
* **Converter dados** de consulta de forma automatizada, evitando códigos repetitivos na criação de entidades a partir de requisições.

---

## 🚨 Exceções Customizadas

A aplicação trata erros utilizando **exceções customizadas**, melhorando a experiência do usuário ao fornecer mensagens claras e compreensíveis. Isso também garante que erros não comprometam o funcionamento do sistema.

### Exemplo de exceção:

```java
public class ConsultationTimeException extends RuntimeException {
    public ConsultationTimeException(String message) {
        super(message);
    }
}
```

### Manipulação no Handler:

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConsultationTimeException.class)
    public ResponseEntity<String> handlerConsultationError(ConsultationTimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
```

---

## 💬 Chat em Tempo Real

Utilizando o protocolo **STOMP** com **WebSocket**, o sistema permite troca de mensagens em tempo real entre usuários conectados. As mensagens não são armazenadas, ou seja, são **voláteis e temporárias**.

### Configuração WebSocket:

```java
@Override
public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.enableSimpleBroker("/topic");
    registry.setApplicationDestinationPrefixes("/app");
}

@Override
public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/websocket").setAllowedOriginPatterns("*").withSockJS();
}
```

---

## 🗓️ Integração com Google Calendar

A integração com a **Google Calendar API** permite que o paciente registre a consulta diretamente na agenda pessoal do Google. A criação do evento inclui:

* Título da consulta
* Horário de início
* Horário de término
* E-mail do usuário

### Requisitos para funcionar:

* Criar um **projeto no Google Cloud Console**
* Habilitar a **Google Calendar API**
* Criar uma **credencial do tipo OAuth 2.0**
* Adicionar usuários de teste no console do projeto

### Configuração no `application.properties`:

```properties
google.calendar.application.name=agendapro
google.calendar.credentials.path=/credentials.json
google.calendar.tokens.directory=tokens
```

### 📍 Processo de autorização:

* Acesse a rota (acesse na web):

```
http://localhost:8080/calendar/authorize
```

* Um link será exibido no terminal. Acesse esse link, selecione o e-mail autorizado e conceda permissões à aplicação.
* Os tokens de autenticação são salvos e reutilizados até expirarem. Após isso, exclua o diretório de tokens para gerar um novo automaticamente.

Link: [Documentação Oficial da API do Google Calendar](https://developers.google.com/workspace/calendar/api/guides/overview?hl=pt-br)
