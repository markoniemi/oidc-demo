# Technical Specification

## Architecture
*   **Style**: Monolithic Application.
*   **Backend**: Spring Boot (Resource Server).
*   **Frontend**: React (Single Page Application), packaged as a WebJar.
*   **Authentication**: OAuth 2.0 / OIDC (Authorization Code Flow).
*   **Database**: PostgreSQL (Production), H2 (Test).

## Technology Stack

### Backend
*   **Java**: 17+
*   **Framework**: Spring Boot 3.x
*   **Web**: Spring Web MVC (REST Controllers)
*   **Persistence**: Spring Data JPA
*   **Security**: Spring Security (OAuth2 Resource Server)
*   **Mapping**: MapStruct
*   **Utilities**: Lombok

### Frontend
*   **Language**: TypeScript
*   **Framework**: React
*   **Build Tool**: Vite
*   **State Management**: React Query (TanStack Query)
*   **UI Library**: React Bootstrap
*   **HTTP Client**: Fetch API

## API Design
*   **Protocol**: REST over HTTP.
*   **Format**: JSON.
*   **Endpoints**:
    *   `/api/users`: User management (CRUD).
    *   `/api/auth`: Authentication related endpoints.

## Deployment
*   Dockerized application.
*   Frontend assets served by Spring Boot backend.
