# 🌱 CarbonOverde API

API RESTful para gestão de compensação de carbono e áreas verdes, desenvolvida em **Java com Spring Boot**.

---

## 📋 Índice

- [🎯 Visão Geral](#-visão-geral)  
- [🛠 Tecnologias](#-tecnologias)  
- [✨ Funcionalidades](#-funcionalidades)  
- [📁 Estrutura do Projeto](#-estrutura-do-projeto)  
- [🚀 Instalação e Execução](#-instalação-e-execução)  
- [🔐 Autenticação](#-autenticação)  
- [📡 Endpoints](#-endpoints)  
- [🛡️ Segurança](#️-segurança)  
- [🗃️ Modelo de Dados](#️-modelo-de-dados)  
- [⚙️ Configuração](#️-configuração)  
- [🔄 Fluxo de Compensação](#-fluxo-de-compensação)  
- [📊 Métricas Ambientais](#-métricas-ambientais)  
- [🐛 Troubleshooting](#-troubleshooting)  
- [🤝 Contribuição](#-contribuição)  
- [📄 Licença](#-licença)

---

## 🎯 Visão Geral

O **CarbonOverde** é uma plataforma que conecta empresas que emitem carbono com áreas verdes disponíveis para compensação ambiental.  
A API gerencia todo o ciclo de compensação, desde o registro de emissões até a alocação de áreas verdes.

---

## 🛠 Tecnologias

- **Java 21** — Linguagem de programação  
- **Spring Boot 3.5.6** — Framework principal  
- **Spring Security** — Autenticação e autorização  
- **JWT** — Tokens de autenticação  
- **PostgreSQL** — Banco de dados  
- **JPA/Hibernate** — ORM  
- **Maven** — Gerenciamento de dependências  
- **Lombok** — Redução de código boilerplate  
- **Validation API** — Validação de dados  

---

## ✨ Funcionalidades

### 👥 Gestão de Usuários
- Três níveis de acesso: `USER`, `ADMIN`, `MANAGER`
- Autenticação via JWT  
- Controle de permissões por cidade  

### 🏢 Gestão de Empresas
- Cadastro de empresas com **CNPJ**  
- Cálculo automático de **CO₂ acumulado**  
- Endereçamento completo com **geolocalização**  

### 📊 Emissões Mensais
- Registro mensal de emissões de CO₂  
- Controle de consumo (energia, água, resíduos)  
- Atualização automática do acumulado  

### 🌳 Áreas Verdes
- Cadastro de parques, praças e bosques  
- Controle de área disponível para compensação  
- Status de implementação e manutenção  

### 💚 Compensações Ambientais
- Vinculação entre empresas e áreas verdes  
- Cálculo de CO₂ compensado  
- Controle de valores investidos e status  

### 📈 Dashboard
- Estatísticas consolidadas  
- Dados para mapas interativos  
- Impacto ambiental equivalente  

---

## 📁 Estrutura do Projeto

```
src/main/java/com/carbonoverde/backend/
├── configs/              # Configurações
│   ├── security/         # Segurança e JWT
│   └── JwtConfig.java    # Configuração JWT
├── controllers/          # Controladores REST
├── dtos/                 # Objetos de Transferência de Dados
│   ├── requests/         # DTOs de entrada
│   ├── responses/        # DTOs de saída
│   └── mappers/          # Conversores Entity/DTO
├── entities/             # Entidades JPA
├── enums/                # Enumerações
├── handlers/             # Tratamento de exceções
├── repositories/         # Interfaces JPA
└── services/             # Lógica de negócio
```

---

## 🚀 Instalação e Execução

### 🔧 Pré-requisitos
- **Java 21**  
- **Maven 3.6+**  
- **PostgreSQL 12+**

### 🗄️ Configuração do Banco

```sql
CREATE DATABASE carbono_verde;
```

### ▶️ Executando a Aplicação

```bash
git clone <repository-url>
cd backend
```

Configure as variáveis de ambiente:

```properties
# application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/carbono_verde
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

Execute a aplicação:

```bash
mvn spring-boot:run
```

A API estará disponível em:  
➡️ **http://localhost:8080**

---

## 🔐 Autenticação

### Login

**POST** `/api/auth/login`

```json
{
  "username": "admin",
  "password": "senha123"
}
```

**Resposta:**

```json
{
  "token": "jwt_token",
  "username": "admin",
  "role": "ADMIN",
  "clientName": "Nome do Usuário"
}
```

### Uso do Token

Inclua no header das requisições:

```
Authorization: Bearer <jwt_token>
```

---

## 📡 Endpoints

### 🔑 Autenticação
- `POST /api/auth/login` — Login na aplicação  

### 🏢 Empresas
- `GET /api/companies` — Listar empresas  
- `GET /api/companies/{id}` — Buscar por ID  
- `POST /api/companies` — Criar empresa  
- `PUT /api/companies/{id}` — Atualizar empresa  
- `DELETE /api/companies/{id}` — Excluir empresa  

### 🌫️ Emissões Mensais
- `GET /api/monthly-emissions` — Listar emissões  
- `GET /api/monthly-emissions/{id}` — Buscar por ID  
- `POST /api/monthly-emissions` — Criar emissão  
- `PUT /api/monthly-emissions/{id}` — Atualizar emissão  
- `DELETE /api/monthly-emissions/{id}` — Excluir emissão  

### 🌳 Áreas Verdes
- `GET /api/green-areas` — Listar áreas verdes  
- `GET /api/green-areas/{id}` — Buscar por ID  
- `POST /api/green-areas` — Criar área  
- `PUT /api/green-areas/{id}` — Atualizar área  
- `DELETE /api/green-areas/{id}` — Excluir área  

### 💚 Compensações
- `GET /api/compensations` — Listar compensações  
- `GET /api/compensations/{id}` — Buscar por ID  
- `POST /api/compensations` — Criar compensação  
- `PUT /api/compensations/{id}` — Atualizar compensação  
- `DELETE /api/compensations/{id}` — Excluir compensação  

### 📈 Dashboard
- `GET /api/dashboard` — Dados consolidados  
- `GET /api/dashboard/map` — Dados para mapa  

### 👤 Usuários
- `GET /api/users` — Listar usuários  
- `GET /api/users/{id}` — Buscar por ID  
- `POST /api/users` — Criar usuário  
- `PUT /api/users/{id}` — Atualizar usuário  
- `DELETE /api/users/{id}` — Excluir usuário  

---

## 🛡️ Segurança

### Hierarquia de Permissões

| Role     | Permissões                              |
|-----------|------------------------------------------|
| USER      | Acesso ao dashboard e visualização       |
| ADMIN     | Gestão de dados da mesma cidade          |
| MANAGER   | Acesso total ao sistema                  |

**Regras de Segurança**
- `ADMIN`: Acesso restrito à cidade do usuário  
- `MANAGER`: Acesso total a todos os dados  
- `USER`: Somente visualização  
- **Validação de Cidade:** Impede acesso *cross-cidade*  

---

## 🗃️ Modelo de Dados

### Principais Entidades

**User**
- name, username, password, email, city, role  
- Implementa `UserDetails` do Spring Security  

**Company**
- name, cnpj, accumulatedCo2  
- Relacionamento: Address, MonthlyEmission, Compensation  

**MonthlyEmission**
- monthReference, emissionsCo2, consumo mensal  
- Relacionamento: Company  

**GreenArea**
- name, areaTotalM2, areaAvailableM2, typeArea, statusArea  
- Relacionamento: Address, Compensation  

**Compensation**
- statusCompensation, co2Compensated, investedValue  
- Relacionamento: Company, GreenArea, MonthlyEmission  

---

## ⚙️ Configuração

```properties
# Banco de dados
spring.datasource.url=jdbc:postgresql://localhost:5432/carbono_verde
spring.datasource.username=admin
spring.datasource.password=password

# JWT
jwt.secret=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
jwt.expiration=86400000
jwt.issuer=carbonoverde-backend

# CORS
cors.allowed-origins=http://localhost:3000
```

### Variáveis de Ambiente Recomendadas

```bash
export DB_URL=your_database_url
export DB_USERNAME=your_username
export DB_PASSWORD=your_password
export JWT_SECRET=your_jwt_secret
```

---

## 🔄 Fluxo de Compensação

1. Empresa é cadastrada  
2. Emissões mensais são registradas  
3. Áreas verdes são mapeadas  
4. Compensação é criada vinculando empresa e área  
5. CO₂ compensado é calculado  
6. Área disponível é atualizada  

---

## 📊 Métricas Ambientais

A API calcula automaticamente:

- 🌳 Árvores equivalentes plantadas  
- 🚗 Carros retirados das ruas  
- 🌲 Área de floresta equivalente (ha)  
- ⚡ Energia economizada (MWh)  
- 📈 Taxas de crescimento e redução mensal  

---

## 🐛 Troubleshooting

| Problema | Solução |
|-----------|----------|
| Erro de CORS | Verifique `SecurityConfig.java` |
| Token Expirado | Ajuste `jwt.expiration` |
| Permissão Negada | Confira `role` e `cidade` do usuário |
| Área Insuficiente | Valide `areaAvailableM2` antes da compensação |

**Logs Debug:**
```properties
logging.level.com.carbonoverde=DEBUG
logging.level.org.springframework.security=DEBUG
```

---

## 🤝 Contribuição

1. Fork o projeto  
2. Crie uma branch para sua feature  
3. Commit suas mudanças  
4. Faça push para a branch  
5. Abra um Pull Request  

---

## 📄 Licença

Este projeto está sob a licença **MIT**.  
Veja o arquivo `LICENSE` para mais detalhes.

---

🌳 **CarbonOverde API — Transformando emissões em florestas! 💚**
