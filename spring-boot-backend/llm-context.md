# Module: Spring Boot Backend

## Purpose
The Resource Server that hosts the business logic and data. It relies on the OIDC Server to validate identities.

## Tech Stack
- **Framework:** Spring Boot Web
- **Security:** Spring Security (OAuth2 Resource Server)

## Key Responsibilities
- Expose REST APIs consumed by `react-frontend`.
- Validate JWT Access Tokens (signature, expiration, issuer).
- Enforce Role-Based Access Control (RBAC) or Scope-based access.
- **Stateless:** Does not maintain user sessions; relies entirely on the Bearer token.

## Configuration
- Must be configured with the JWK Set URI from the `oidc-server` to verify signatures.