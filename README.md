# Expense Reimbursement REST API

Enterprise-style expense 
reimbursement system built with **Java 17**,
**Spring Boot**, and **PostgreSQL**.

Implements a multi-role approval workflow (Employee, Manager, Finance) with clean labeled architecture with role-based authorization.

---

## Overview

This project simulates a real-world corporate expense system 

- Employees submit expenses
- Managers can approve / reject
- Financial reimbursements approved expenses
- Role-based access control enforced with request headers

Designed to reflect enterprise backend standards:

- DTO separation
- Service-layer validation
- Repository abstraction
- Clean controller contracts
- Dockerized database

---

## Architecture

Layout design:

1. Controller
2. Service
3. Repository
4. Database

```arduino
controller/
service/
repository/
entity/
dto/
config/
```

- Controllers handle HTTP contracts only
- Services contain business rules
- Repositories use Spring Data JPA
- Entities map to PostgreSQL tables
- DTOs isolate API contracts from persistence models

---

## Tech Stack 

- Java 17
- Spring Boot 4.x
- Spring Data JPA 
- PostgreSQL
- Docker
- Maven

---

## Getting Started

### 1. Clone the repository

``` bash
git clone https://github.com/dantemallozzi05/expense-reimbursment-api.git
cd expense-reimbursment-api
```

### 2. Start PostgreSQL with Docker

``` bash
docker compose up -d
```

### 3. Run the application

``` bash
mvn spring-boot:run
```

---

## Business Workflow
``` bash
SUBMITTED > APPROVED > REIMBURSED
        |
        L> REJECTED
```

Validation rules enforced in Service layer:

- Only managers can approve / reject expenses
- Only Finance role can reimburse
- Cannot reimburse an expense unless it's approved
- Cannot approve an expense twice



