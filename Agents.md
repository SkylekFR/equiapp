# Project Architectural Rules & Guidelines

This document outlines the mandatory architectural patterns and principles to be followed by all agents working on this project.

## 1. Clean Architecture & SOLID Principles
- Adhere strictly to **Clean Architecture** (Presentation, Domain, Data layers).
- Follow **SOLID** principles in every class and module design.
- Ensure high cohesion and low coupling between layers.

## 2. Unidirectional Data Flow (UDF)
- Data flow must always follow: **Data → Domain → Presentation**.
- State flows from the ViewModel to the UI.
- Events (Intents/Actions) flow from the UI to the ViewModel.

## 3. Dependency Injection
- Use **Koin** for all dependency injection.
- Define modules for each layer (e.g., `appModule`, `networkModule`, `dataModule`).
- Inject dependencies via constructors.

## 4. Networking
- Use **Ktor** as the primary networking library for multiplatform communication.

## 5. Domain Layer & Use Cases
- **Use Cases** must be **platform agnostic** (reside in `commonMain`).
- Each Use Case should perform a single business operation.
- Use Cases must return a **Custom Result class** (see section 9).

## 6. Repository Pattern
- Repository **interfaces** must reside in the **Domain** layer.
- Repository **implementations** must reside in the **Data** layer.
- Presentation and Domain layers should only interact with Repository interfaces.

## 7. Datasources Pattern
- Wrap network calls and local storage (Database/Preferences) in **Datasources**.
- Data sources should be injected into Repositories.
- Repositories orchestrate data from one or multiple datasources.

## 8. UI & Navigation
- Build the UI using **Jetpack Compose / Compose Multiplatform**.
- Use **Navigation3** for navigation across screens.
- Keep Composables stateless where possible; use ViewModels for managing state.

## 9. Custom Result Class
- Avoid using `kotlin.Result` for domain errors.
- Use a sealed class/interface to represent Success and Failure.
- **Strong Error Typing**: Errors must be modeled using **sealed classes/interfaces** or **enums**.
- **Avoid generic types** like `Throwable`, `Exception`, or `String` messages within error objects. This ensures that the UI can handle each error case exhaustively and type-safely.
- Example:
  ```kotlin
  sealed interface AppResult<out T> {
      data class Success<T>(val data: T) : AppResult<T>
      data class Failure(val error: AppError) : AppResult<Nothing>
  }

  sealed interface AppError {
      sealed interface Network : AppError {
          data object NoConnection : Network
          data object Timeout : Network
          data object Unauthorized : Network
          data object ServerError : Network
      }
      sealed interface Business : AppError {
          data object CourseNotFound : Business
          data object InsufficientCredits : Business
          data object DeadlineExpired : Business
      }
      data object Unknown : AppError
  }
  ```
