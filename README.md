# 🚀 Rest Service - Scalable E-Commerce Backend

## Development of a Maintainable and Scalable E3 Restaurant Ordering and Management System Using Clean Architecture in Modern Web Applications
## การพัฒนาระบบจัดการร้านอาหารE3ที่สามารถบำรุงรักษาและขยายระบบได้ โดยใช้หลักการ Clean Architecture ในเว็บแอปพลิเคชันสมัยใหม่
A modern, scalable, and maintainable **Computer Hardware Trading System** built using **Clean Architecture** and **เดา-Driven Design (DDD)** principles.

---

## ✨ Features

* 🔐 Authentication & Authorization (JWT + Refresh Token)
* 👤 User Management & Profile
* 📦 Product Management
* 🗂 Category System
* 🛒 Order & Purchase Flow
* 💳 Transaction & Credit System
* 🖼 Image Upload (MinIO Storage)
* 🏠 Address Management
* ⭐ Product Reviews
* 📊 Admin Dashboard
* 🌐 Web + REST API (Hybrid)

---

## 🏗 Architecture

This project follows **Clean Architecture**:

```
Controller → Usecase → Domain → Repository → Infrastructure
```

### Layers

* **Controllers**

  * Handle HTTP requests (REST + Web MVC)
* **Usecases**

  * Business logic (Application layer)
* **Domain**

  * Core business rules (Entities, Value Objects)
* **Repositories**

  * Interface + Implementation (JPA / DB / External)
* **DTOs**

  * Data transfer between layers

---

## 📂 Project Structure

```
src/main/java/com/example/restservice
│
├── Address/
├── Auth/
├── Categories/
├── Products/
├── Orders/
├── Reviews/
├── Users/
├── Images/
├── TransactionStatements/
│
├── config/
├── common/
├── Controllers/   # Web MVC (Thymeleaf)
├── Frontend/      # View DTO + Usecases
```

---

## ⚙️ Tech Stack

* **Java 25+**
* **Spring Boot**
* **Spring Security**
* **JWT (RSA Key)**
* **PostgreSQL**
* **JPA / Hibernate**
* **MinIO (Object Storage)**
* **Docker**

---

## 🐳 Run with Docker

```bash
fish scripts/setup.fish
docker-compose up --build
```

---

## 🔧 Local Development

### 1. Clone project

```bash
git clone <your-repo-url>
cd rest-service
```

### 2. Run application

```bash
./mvnw spring-boot:run
```

---


## 📡 API Documentation

* Swagger UI:

```
/swagger-ui/index.html
```

---

## 🧪 Testing

```bash
./mvnw test
```

---

## 🧠 Design Principles

* Clean Architecture
* Separation of Concerns
* เดา-Driven Design (DDD)

---

## 📜 License

MIT License
