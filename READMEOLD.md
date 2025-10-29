# API de Pegada de Carbono

Este projeto é uma API desenvolvida em Java com Spring Boot, voltada para o gerenciamento de dados relacionados à pegada de carbono, sustentabilidade e conscientização ambiental. O objetivo é apoiar empresas e indivíduos a monitorar emissões, compensações e promover práticas sustentáveis.

## Visão Geral
A API facilita o registro, cálculo, consulta e gestão de informações ambientais, como:
- Emissões mensais
- Áreas verdes
- Compensações de carbono
- Informações de empresas e usuários

Tudo isso buscando auxiliar a sociedade na redução do impacto ambiental e fortalecimento da responsabilidade ecológica.

---

## Funcionalidades Principais
- Cadastro e autenticação de usuários e empresas
- Gerenciamento de áreas verdes e suas características
- Registro e acompanhamento das emissões de carbono
- Lançamento de compensações ambientais
- Consulta simplificada dos dados via endpoints RESTful

---

## Tecnologias Utilizadas
- Java 17+
- Spring Boot
- Spring Security (JWT)
- JPA (Hibernate)
- Banco de Dados relacional (ex: PostgreSQL, MySQL)

---

## Como Executar o Projeto

1. Clone o repositório
```sh
git clone <url-do-repositorio>
```
2. Acesse a pasta do projeto
```sh
cd backend-spring-boot
```
3. Configure as variáveis em `src/main/resources/application.properties` para conectar ao banco de dados.
4. Execute o projeto com o Maven Wrapper:
```sh
./mvnw spring-boot:run
```
Ou, no Windows:
```sh
mvnw.cmd spring-boot:run
```

A API estará disponível por padrão em `http://localhost:8080`.

---

## Estrutura Básica de Pastas
- **controllers/**: Camada de controle e endpoints da API
- **entities/**: Entidades JPA e modelos do domínio
- **dtos/**: Objetos de transferência de dados (Data Transfer Objects)
- **repositories/**: Interfaces de persistência com o banco de dados
- **services/**: Lógica de negócio e serviços
- **configs/**: Configurações do Spring, segurança e JWT

---

## Licença
Este projeto é open-source, sob a licença MIT.

---

> Projeto desenvolvido com foco em sustentabilidade, responsabilidade ambiental e apoio à conscientização ecológica.
