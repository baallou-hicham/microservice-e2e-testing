# 🏦 Bank Microservices Project

## 📖 Description

This project is a banking application based on a **microservices architecture**.  
It includes:

- **Customer-Service**: Manages customers (CRUD, business rules)
- **Account-Service**: Manages bank accounts (create, debit, credit, transfer, history)
- **Discovery Service**: Eureka Server
- **API Gateway**: Spring Cloud Gateway
- **Configuration Service**: Centralized configuration
- **Security**: OAuth2 / OIDC with Keycloak
- **CI/CD**: Jenkins, Docker, Kubernetes

The goal of this project is also **educational**, demonstrating how to organize testing in a microservices environment.

---

## 🏗️ Architecture Overview

```bash

                 🌐 CLIENT (Web / Mobile / API)
                              │
                              ▼
                 🚪 API GATEWAY (Spring Cloud Gateway)
                              │
     ┌────────────────────────┴────────────────────────┐
     ▼                                                 ▼
👤 Customer-Service                           💳 Account-Service
(Customer, Email, CRUD)                     (Accounts, Transactions)
      │                                               │
      ▼                                               ▼
🗄️ DB Customer                                   🗄️ DB Account

```

---

## 🧪 Testing Strategy

The testing pyramid in this project:

            🔴 E2E TESTS (few)
    ─────────────────────────────────────
    Full business scenarios:
    - Create customer → create account → credit
    - Transfer money between accounts

            🟡 INTEGRATION TESTS
    ─────────────────────────────────────
    - Customer-Service + DB (JPA Test)
    - REST communication between services
    - API layer tests (Controller + Service + DB)

            🟢 UNIT TESTS (many)
    ─────────────────────────────────────
    - CustomerServiceImpl (Mockito)
    - Isolated business logic
    - No DB, no network


---

### 🔹 1. Unit Tests

- **Scope**: `CustomerServiceImpl`
- **Tools**: JUnit 5, Mockito
- **Objective**: Test **business logic only**
- **Examples**:
    - `shouldCreateCustomerWhenEmailNotExists()`
    - `shouldThrowExceptionWhenEmailAlreadyExists()`
    - `shouldGetCustomerById()`
    - `shouldThrowExceptionWhenCustomerNotFound()`

---

### 🔹 2. Integration Tests

- **Scope**: Repository + DB layer
- **Tools**: `@DataJpaTest`, H2
- **Objective**: Verify **persistence works correctly**
- **Examples**:
    - `shouldFindCustomerByEmail()`
    - `shouldFindCustomersByFirstName()`

---

### 🔹 3. Inter-Service Tests

- **Scope**: Customer-Service ↔ Account-Service
- **Tools**: REST Assured, Testcontainers
- **Objective**: Test **communication between services**

---

### 🔹 4. End-to-End (E2E) Tests

- **Scope**: Full system (Client → Gateway → Services → DB)
- **Tools**: Postman, Cypress, Selenium
- **Objective**: Validate **full business scenarios**
- **Examples**:
    - Create a customer → create an account → credit → verify balance

---

## 📊 Test Types Summary

| Type of Test     | Scope                 | Tools                      | Objective                        |
|-----------------|----------------------|---------------------------|----------------------------------|
| 🟢 Unit Tests    | 1 class              | JUnit 5, Mockito          | Business logic                   |
| 🟡 Integration   | 1 service + DB       | Spring Test, H2           | Persistence / Repository         |
| 🔵 Inter-Service | Multiple services    | REST Assured, Testcontainers | Communication between services |
| 🔴 E2E           | Full system          | Postman, Cypress          | Full business scenario           |

> ✅ Pedagogical rule:
> - Many **unit tests**
> - Some **integration tests**
> - Few **E2E tests**

---

## 📌 Key Takeaways

1. **Unit tests**: Fast, isolated, test business logic only
2. **Integration tests**: Verify interactions with DB or other components
3. **Inter-service tests**: Ensure proper communication between microservices
4. **E2E tests**: Validate critical business workflows, few in number