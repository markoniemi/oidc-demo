# Module: React Frontend

## Purpose
The public-facing Single Page Application (SPA) that users interact with. It acts as the OAuth2 "Public Client".

## Tech Stack
- **Framework:** React
- **Language:** TypeScript (Preferred)
- **Build Tool:** Vite or Create React App
- **OIDC Library:** `react-oidc-context` or `oidc-client-ts`

## Key Responsibilities
- Authenticate users via the `oidc-server`.
- Manage Access Tokens and ID Tokens.
- Make authorized requests to `spring-boot-backend` using the Bearer token.
- Handle login/logout redirects.
- Implements PKCE flow for security.