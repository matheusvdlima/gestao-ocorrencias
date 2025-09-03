# Gestão de Ocorrências

[![Java](https://img.shields.io/badge/Java-17-007396?logo=openjdk)](https://openjdk.org/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?logo=springboot)](https://spring.io/projects/spring-boot)
[![Angular](https://img.shields.io/badge/Angular-19-DD0031?logo=angular)](https://angular.dev/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-336791?logo=postgresql)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?logo=docker)](https://www.docker.com/)

Sistema fullstack para gestão de ocorrências, com **Spring Boot + Angular + PostgreSQL**, rodando em containers via **Docker Compose** ou diretamente no ambiente local.

---

## 🚀 Tecnologias
- **Backend:** Java 17, Spring Boot, Spring Security (JWT), Flyway, Maven, Swagger/OpenAPI  
- **Frontend:** Angular 19, Angular Material, ngx-toastr, ngx-charts  
- **Banco:** PostgreSQL 16  
- **Infra:** Docker, Docker Compose, Nginx  

---

## ⚙️ Como Rodar

### 🔹 Opção 1: Com Docker (recomendado)

#### Pré-requisitos
- [Docker](https://docs.docker.com/get-docker/)  
- [Docker Compose](https://docs.docker.com/compose/)  

#### Passos
```bash
# clonar repositório
git clone https://github.com/seu-usuario/gestao-ocorrencias.git
cd gestao-ocorrencias

# subir os containers
docker compose up -d --build
```

---

### 🔹 Opção 2: Localmente (sem Docker)

#### Pré-requisitos
- [Java 17](https://adoptium.net/)  
- [Maven](https://maven.apache.org/)  
- [Node.js](https://nodejs.org/)  
- Banco de dados PostgreSQL rodando localmente

#### Backend
```bash
cd backend
mvn clean spring-boot:run
```

#### Frontend
```bash
cd frontend
npm install
npm start
```

---

## 🌐 Endpoints

- **Frontend:** http://localhost:4200  
- **Backend:** http://localhost:8080  
- **Swagger UI:** http://localhost:8080/swagger-ui.html  
- **Banco de Dados:** postgres://localhost:5434/incidents_db  

---

## 👤 Usuário Padrão

O sistema já vem com um usuário administrador para acesso inicial:

- **Usuário:** matheus@incidents.com  
- **Senha:** 123456
