# Implementation Plan: Compliance Refactoring

This document outlines the steps required to bring the `oidc-demo` project into compliance with the `copilot-instructions.md`.

## Phase 1: Project Configuration & Dependencies (Backend)

**Goal**: Align `spring-boot-backend/pom.xml` with the required technology stack.

1.  **Remove Apache CXF Dependencies**:
    *   Remove `cxf-spring-boot-starter-jaxrs`
    *   Remove `cxf-rt-frontend-jaxrs`
    *   Remove `cxf-spring-boot-starter-jaxws`
    *   Remove `jackson-jakarta-rs-json-provider`
    *   Remove `jackson-jakarta-rs-xml-provider`
2.  **Add Required Dependencies**:
    *   Add `spring-boot-starter-validation` (for Bean Validation).
    *   Add `org.postgresql:postgresql` (PostgreSQL driver).
    *   Add `org.mapstruct:mapstruct` (for Entity-DTO mapping).
3.  **Add Build Plugins/Processors**:
    *   Add `mapstruct-processor` to the `maven-compiler-plugin` annotation processor paths (or as a dependency with `<scope>provided</scope>` depending on Lombok interaction). Ensure Lombok runs *before* MapStruct.

## Phase 2: Backend Architecture Refactoring

**Goal**: Transition from JAX-RS (CXF) to Spring Web MVC and implement DTO pattern.

1.  **Create DTOs**:
    *   Create package `org.example.dto`.
    *   Create `UserDTO` (response), `UserCreateDTO` (request), `UserUpdateDTO` (request).
    *   Ensure DTOs use Lombok (`@Data`) and Validation annotations (`@NotBlank`, etc.).
2.  **Implement MapStruct Mappers**:
    *   Create package `org.example.mapper`.
    *   Create `UserMapper` interface with `@Mapper(componentModel = "spring")`.
3.  **Refactor Entities (`org.example.model`)**:
    *   Remove JAXB annotations (`@XmlRootElement`, `@XmlAccessorType`).
    *   Remove Jackson annotations (`@JsonIgnoreProperties`) intended for direct API exposure.
    *   Remove Javadoc comments.
4.  **Refactor Services (`org.example.service`)**:
    *   Remove `@WebService` and `@InterfaceLog` annotations.
    *   Replace `Apache Commons Validate` with standard Bean Validation or manual checks throwing custom exceptions handled by `@ControllerAdvice`.
    *   Update methods to accept/return DTOs OR keep them working on Entities but ensure Controllers handle the mapping. *Recommendation: Services work on Entities, Controllers map DTOs.*
5.  **Create REST Controllers (`org.example.controller`)**:
    *   Create `UserController` annotated with `@RestController` and `@RequestMapping("/api/users")`.
    *   Implement endpoints: `GET /`, `GET /{id}`, `POST /`, `PUT /{id}`, `DELETE /{id}`.
    *   Use `@PreAuthorize` for security checks.
    *   Inject `UserService` and `UserMapper`.
6.  **Cleanup**:
    *   Remove `org.example.config.RestConfig` (CXF config).
    *   Remove `org.example.rest` (ExceptionMappers for CXF). Implement `GlobalExceptionHandler` with `@ControllerAdvice` for Spring MVC.

## Phase 3: Frontend Refactoring

**Goal**: Align directory structure and state management with instructions.

1.  **Directory Structure**:
    *   Rename `react-frontend/src/api` to `react-frontend/src/services`.
    *   Update all import paths.
2.  **React Query Integration**:
    *   Refactor `UsersContainer.tsx` (and others) to use `useMutation` for `create`, `update`, and `delete` operations.
    *   Ensure `queryClient.invalidateQueries` is called on success to refresh data.
3.  **Service Layer**:
    *   Ensure services return Promises that resolve to data, not `Response` objects (or handle error throwing consistently).

## Phase 4: Documentation & Final Polish

1.  **Documentation**:
    *   Create `TechnicalSpecification.md` in the root (or `docs/`) defining the API contract and Architecture.
    *   Create `RequirementsSpecification.md` in the root (or `docs/`) defining the functional requirements.
2.  **Code Style**:
    *   Scan for and remove any remaining Javadoc in the backend.
    *   Verify Google Java Format compliance.

## Execution Order

1.  Phase 1 (Dependencies)
2.  Phase 2 (Backend Refactoring) - *This is the largest task.*
3.  Phase 3 (Frontend Refactoring)
4.  Phase 4 (Docs)
