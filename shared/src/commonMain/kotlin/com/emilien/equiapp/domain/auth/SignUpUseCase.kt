package com.emilien.equiapp.domain.auth

import com.emilien.equiapp.domain.AppResult

class SignUpUseCase(private val repository: AuthRepository) {
    suspend fun execute(email: String, password: String): AppResult<UserSession, AuthError> {
        if (email.isBlank() || password.length < 6) {
            return AppResult.Failure(AuthError.Business.WeakPassword)
        }
        return repository.signUp(email, password)
    }
}
