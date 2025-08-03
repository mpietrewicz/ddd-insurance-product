# Insurance Product System - DDD & Hexagonal Architecture POC

## 📋 About
A proof-of-concept project implementing an insurance product system using Domain-Driven Design (DDD) and hexagonal architecture. The system is designed with emphasis on clean business domain and layer separation.

## 🏗️ Architecture
- **Hexagonal Architecture** (Ports & Adapters)
  - Domain core isolated from technical details
  - Communication through ports and adapters
  - Dependency inversion principle

### 📦 Module Structure
```
product/
├── domain/         # Domain core - aggregates, policies, events
├── domain-api/     # Contracts and ports
├── infrastructure/ # Infrastructure adapters
├── app/            # Application layer
├── web-api/        # REST API with HATEOAS
├── read-model/     # Read model (CQRS)
└── common-util/    # Shared utilities
```

## 🎯 Key Aspects
- Clean business domain encapsulated in aggregates
- Domain policies enforcing business rules
- HATEOAS implementation in API
- Write/Read model separation

## 🛠️ Technology Stack
- Java 21
- Spring Boot 3.4.2
- Spring Data JPA

## 🚀 Getting Started
1. Build the project (this will compile and run all tests):
```
mvn clean install
```
2. Run the application (this will start the Spring Boot application):
```
mvn spring-boot:run -pl app
```

## 📝 License
GNU General Public License v3.0 - see [LICENSE](LICENSE) file

## ⚠️ Project Status
Proof of Concept - demonstration project, not recommended for production use.
