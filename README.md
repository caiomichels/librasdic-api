# librasdic-api

Backend da aplicação LibrasDic, desenvolvido com Spring Boot.

## Tecnologias

- Java 25
- Spring Boot 4
- Spring Web
- Spring Data JPA
- MariaDB
- Maven Wrapper (`mvnw`)

## Pré-requisitos

- Java 25 instalado
- MariaDB em execução
- Banco de dados criado (ex.: `librasdic`)
- Usuário com permissão no banco (padrão atual no projeto: `libras-admin`)

## Configuração

As configurações principais estão em `src/main/resources/application.properties`:

- `spring.datasource.url=jdbc:mariadb://localhost:3306/librasdic`
- `spring.datasource.username=libras-admin`
- `spring.datasource.password=libras`
- `spring.jpa.hibernate.ddl-auto=update`
- `api.security.jwt.secret=secret`

Ajuste esses valores conforme seu ambiente antes de executar em produção.

## Script de inicialização do banco (`db.sql`, `load_data.sql`)

O projeto utiliza um script SQL chamado `db.sql` para inicializar o banco de dados com dados base da aplicação, e outro `load_data.sql` para popula-lo. Esse seundo é necessário alterar as ocorrências de BASE_PATH para o caminho da pasta startup na sua máquina, por exemplo: `/home/user/workspace/librasdic-api/startup`.

Descrição esperada do script:

- Criação de estrutura auxiliar inicial (quando aplicável)
- Carga de dados iniciais (mãos, classes gramaticais, origens, assuntos, sinais, palavras etc.)
- Preparação do ambiente para uso da API sem necessidade de cadastro manual completo

## Como executar

No Windows (PowerShell):

```bash
cd librasdic-api
.\mvnw.cmd spring-boot:run
```

No Linux/macOS:

```bash
cd librasdic-api
./mvnw spring-boot:run
```

A API sobe por padrão em:

- `http://localhost:8080`

## Build e testes

No Windows (PowerShell):

```bash
cd librasdic-api
.\mvnw.cmd clean test
.\mvnw.cmd clean package
```

No Linux/macOS:

```bash
cd librasdic-api
./mvnw clean test
./mvnw clean package
```

## Observações

- O empacotamento está como `war`.
- Em desenvolvimento local, a execução via Spring Boot Maven Plugin é suficiente.
