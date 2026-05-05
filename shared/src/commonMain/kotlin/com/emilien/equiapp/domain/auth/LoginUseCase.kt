package com.emilien.equiapp.domain.auth

import com.emilien.equiapp.domain.AppResult

class LoginUseCase(private val repository: AuthRepository) {
    suspend fun execute(email: String, password: String): AppResult<UserSession, AuthError> {
        if (email.isBlank() || password.isBlank()) {
            return AppResult.Failure(AuthError.Business.InvalidCredentials)
        }
        return repository.login(email, password)
    }
}
