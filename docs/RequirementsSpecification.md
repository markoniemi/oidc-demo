# Requirements Specification

## Overview
The application is a User Management System secured by OpenID Connect.

## Functional Requirements

### User Management
*   **List Users**: Authenticated users can view a list of all registered users.
*   **View User**: Authenticated users can view details of a specific user.
*   **Create User**: Authorized users (Admin) can create new users.
*   **Update User**: Authorized users (Admin) can update existing user details.
*   **Delete User**: Authorized users (Admin) can delete users.

### Authentication & Authorization
*   **Login**: Users must log in via the OIDC Provider.
*   **Logout**: Users can log out, terminating the session.
*   **Role-Based Access Control**:
    *   `ROLE_USER`: Can view users.
    *   `ROLE_ADMIN`: Can create, update, and delete users.

## Non-Functional Requirements
*   **Security**: All API endpoints must be secured. Passwords must not be stored in plain text (handled by IdP).
*   **Performance**: API response time should be under 200ms for standard requests.
*   **Usability**: The UI should be responsive and accessible.
