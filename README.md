# Gestão de Ocorrências

[![Java](https://img.shields.io/badge/Java-21-007396?logo=openjdk)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?logo=springboot)](https://spring.io/projects/spring-boot)
[![Angular](https://img.shields.io/badge/Angular-19-DD0031?logo=angular)](https://angular.dev/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-336791?logo=postgresql)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?logo=docker)](https://www.docker.com/)

Sistema fullstack para gestão de ocorrências, com **Spring Boot + Angular + PostgreSQL**, rodando em containers via **Docker Compose**.

---

## 🚀 Tecnologias
- **Backend:** Java 21, Spring Boot, Spring Security (JWT), Flyway, Maven, Swagger/OpenAPI  
- **Frontend:** Angular 19, Angular Material, ngx-toastr, ngx-charts  
- **Banco:** PostgreSQL 16  
- **Infra:** Docker, Docker Compose, Nginx  

---

## ⚙️ Como Rodar

### Pré-requisitos
- [Docker](https://docs.docker.com/get-docker/)  
- [Docker Compose](https://docs.docker.com/compose/)  

### Passos
```bash
# clonar repositório
git clone https://github.com/seu-usuario/gestao-ocorrencias.git
cd gestao-ocorrencias

# subir os containers
docker compose up -d --build

-> Endpoints

Frontend: http://localhost:4200

Backend: http://localhost:8080

Swagger UI: http://localhost:8080/swagger-ui.html

Banco (local): postgres://localhost:5434/incidents_db