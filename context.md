# Project Context: OIDC Demo

## Overview
This is a multi-module Maven project demonstrating a complete OpenID Connect (OIDC) flow.
It consists of a Single Page Application (SPA) frontend, a Resource Server, and an Authorization Server.

## Architecture
- **Build System:** Maven (Multi-module)
- **Java Version:** Java 21
- **Spring Boot Version:** 3.2+

## Modules
1. **react-frontend:** The client application (SPA).
2. **oidc-server:** The Identity Provider (IdP) / Authorization Server.
3. **spring-boot-backend:** The Resource Server (API).

## Development Standards
- **Code Style:** Standard Java conventions.
- **Documentation:** All APIs should be documented.
- **Security:** 
  - Strict adherence to OAuth 2.1 / OIDC standards.
  - No secrets stored in plain text.
  - Use PKCE (Proof Key for Code Exchange) for the public client.