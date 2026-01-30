# Module: OIDC Server

## Purpose
Acts as the central Identity Provider (IdP). It issues tokens to the frontend.

## Tech Stack
- **Framework:** Spring Boot
- **Library:** Spring Authorization Server

## Key Responsibilities
- Manage User Accounts (in-memory or database).
- Register Clients (e.g., the React Frontend).
- Issue JWTs (Access Tokens, ID Tokens, Refresh Tokens).
- Host the Login Page.
- Expose `.well-known/openid-configuration` endpoints.
- Validate scopes and permissions.