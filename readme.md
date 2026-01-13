Project analysis from Gemini:

# Project Analysis

This project is a comprehensive demonstration of OpenID Connect (OIDC) integration, consisting of three main components:

1.  **`oidc-server`**: Acts as the Identity Provider (IdP) using Spring Authorization Server.
2.  **`spring-boot-backend`**: Acts as the Resource Server, protecting API endpoints.
3.  **`react-frontend`**: The Client application using `react-oidc-context`.

## Architecture Overview

*   **`oidc-server`**: Configured with a single client (`oidc-test`) and a default `admin` user.
*   **`spring-boot-backend`**: Validates JWTs issued by the `oidc-server`. It also serves the compiled React frontend in production scenarios.
*   **`react-frontend`**: Handles the OIDC flow (Authorization Code flow) and sends access tokens to the backend.

## Security Implementation

The backend (`WebSecurityConfig.java`) implements a hybrid security model with dual security chains:

1.  **Legacy Chain (@Order(1))**: Activates for requests with "Legacy" JWTs (signed with a local HMAC512 secret).
2.  **OIDC Chain (@Order(2))**: Standard OAuth2 Resource Server chain that validates JWTs against the `oidc-server`.

This setup supports both legacy/internal clients and modern OIDC clients simultaneously.

## Key Observations

*   **Secrets**: Hardcoded secrets are present in `JwtToken.java`, `application.yml`, and `application.properties`.
*   **Frontend**: `OidcService.ts` uses `client_secret_basic`, exposing the secret in the browser. PKCE is recommended for SPAs.
*   **Deployment**: `WebConfig.java` is configured to serve the React app from a JAR path, facilitating a unified deployment.
