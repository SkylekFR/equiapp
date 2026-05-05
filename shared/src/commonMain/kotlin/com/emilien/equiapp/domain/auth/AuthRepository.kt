package com.emilien.equiapp.domain.auth

import com.emilien.equiapp.domain.AppResult

interface AuthRepository {
    suspend fun login(email: String, password: String): AppResult<UserSession, AuthError>
    suspend fun signUp(email: String, password: String): AppResult<UserSession, AuthError>
    suspend fun logout()
    fun getSession(): UserSession?
}
