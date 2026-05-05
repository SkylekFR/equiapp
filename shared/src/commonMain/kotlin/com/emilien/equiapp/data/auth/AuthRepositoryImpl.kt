package com.emilien.equiapp.data.auth

import com.emilien.equiapp.domain.AppResult
import com.emilien.equiapp.domain.auth.*
import com.emilien.equiapp.network.AuthRemoteDataSource
import com.emilien.equiapp.network.model.LoginRequest
import com.emilien.equiapp.network.model.SignUpRequest
import com.russhwolf.settings.Settings

class AuthRepositoryImpl(
    private val dataSource: AuthRemoteDataSource,
    private val settings: Settings
) : AuthRepository {

    private var currentSession: UserSession? = null

    init {
        val userId = settings.getStringOrNull("user_id")
        val email = settings.getStringOrNull("email")
        val accessToken = settings.getStringOrNull("access_token")
        val refreshToken = settings.getStringOrNull("refresh_token")

        if (userId != null && email != null && accessToken != null && refreshToken != null) {
            currentSession = UserSession(userId, email, accessToken, refreshToken)
        }
    }

    override suspend fun login(email: String, password: String): AppResult<UserSession, AuthError> {
        return try {
            val response = dataSource.login(LoginRequest(email, password))
            val session = UserSession(
                userId = response.user.id,
                email = response.user.email ?: email,
                accessToken = response.accessToken,
                refreshToken = response.refreshToken
            )
            saveSession(session)
            AppResult.Success(session)
        } catch (e: Exception) {
            // Mapping logic should be more detailed based on response body/code
            AppResult.Failure(AuthError.Business.InvalidCredentials)
        }
    }

    override suspend fun signUp(email: String, password: String): AppResult<UserSession, AuthError> {
        return try {
            val response = dataSource.signUp(SignUpRequest(email, password))
            val session = UserSession(
                userId = response.user.id,
                email = response.user.email ?: email,
                accessToken = response.accessToken,
                refreshToken = response.refreshToken
            )
            saveSession(session)
            AppResult.Success(session)
        } catch (e: Exception) {
            AppResult.Failure(AuthError.Business.EmailAlreadyExists)
        }
    }

    override suspend fun logout() {
        currentSession = null
        settings.clear()
    }

    override fun getSession(): UserSession? = currentSession

    private fun saveSession(session: UserSession) {
        currentSession = session
        settings.putString("user_id", session.userId)
        settings.putString("email", session.email)
        settings.putString("access_token", session.accessToken)
        settings.putString("refresh_token", session.refreshToken)
    }
}
