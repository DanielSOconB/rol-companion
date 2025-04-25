# README.md

## 🎲 rol-companion
App web para gestionar campañas de rol con tus amigos. Construida en Spring Boot + PostgreSQL.

---

## 🚀 Cómo iniciar

### 1. Requisitos
- Java 24
- Maven 3.8+
- Docker & Docker Compose

### 2. Compilar la app (sin tests)
```bash
mvn clean package -DskipTests
```

### 3. Iniciar con Docker Compose
```bash
docker-compose up --build -d
```
Esto levanta:
- PostgreSQL (con volumen persistente)
- Spring Boot App en: [http://localhost:8080](http://localhost:8080)

### 4. Parar contenedores
```bash
docker-compose down
```

---

## 🧪 Endpoints REST principales

### `GET /api/campaigns` – Lista campañas
### `GET /api/players` – Lista jugadores
### `POST /api/campaigns` – Crear campaña
```json
{
  "name": "Nueva Campaña",
  "slug": "nueva-campaña",
  "status": "ACTIVE"
}
```

🧪 Importa la colección completa desde:
📥 [rol-companion.postman.json](./rol-companion-postman-collection.json)

---

## ⚙️ Configuración
Archivo `application.yml` o `application-test.yml` en `src/main/resources`

---

## 📦 Estructura del Proyecto
```
📁 controller - APIs REST
📁 service    - Lógica de negocio
📁 repository - Acceso a datos
📁 model      - Entidades JPA
```

---

## 🧙 Autor
Desarrollado por Daniel Salvador Ocon Bounitskaia

---
