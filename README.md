# Loan Request Service - Microservices Architecture

This project implements a loan request system using CQRS (Command Query Responsibility Segregation) pattern with separate command and query services.

## Architecture Overview

- **loan-request-command**: Handles write operations and business logic
- **loan-request-query**: Handles read operations and projections
- **shared**: Common domain events, DTOs, and infrastructure components

## Services

### Loan Request Command Service
- **Port**: 8086
- **Database**: loan_request_command (MongoDB)
- **Responsibilities**: 
  - Create loan requests from approved loan proposals
  - Process loan request approvals/rejections
  - Publish domain events

### Loan Request Query Service
- **Port**: 8087
- **Database**: loan_request_query (MongoDB)
- **Responsibilities**:
  - Maintain read-optimized projections
  - Handle queries for loan request data
  - Listen to domain events for projection updates

## RabbitMQ Integration

### Consumes Events From:
- `loan.proposal.fanout` (from sbicloud-bd-v3-poc-java loan-proposal-command)

### Publishes Events To:
- `loan.request.exchange` with routing keys:
  - `loan.request.created`
  - `loan.request.approved` 
  - `loan.request.rejected`

## Getting Started

### Prerequisites
- Java 21
- Docker & Docker Compose
- Gradle

### Running the Services

1. **Build the project:**
   ```bash
   ./gradlew clean build
   ```

2. **Start infrastructure services:**
   ```bash
   docker-compose up -d mongodb rabbitmq
   ```

3. **Start the applications:**
   ```bash
   ./gradlew :loan-request-command:bootRun
   ./gradlew :loan-request-query:bootRun
   ```

4. **Or run everything with Docker:**
   ```bash
   docker-compose up -d
   ```

### Health Checks
- Command Service: http://localhost:8083/api/v1/loan-requests/health
- Query Service: http://localhost:8084/api/v1/loan-requests/health
- RabbitMQ Management: http://localhost:15672 

## Integration with Loan Proposal Service

This service automatically creates loan requests when loan proposals are approved in the `sbicloud-bd-v3-poc-java` system. Ensure both RabbitMQ instances are connected or use the same RabbitMQ instance for proper event flow.



## Development

The project follows Domain-Driven Design (DDD) principles with:
- Aggregate roots for business logic
- Domain events for decoupling
- Repository pattern for data access
- CQRS for read/write separation# loan-request-v3
