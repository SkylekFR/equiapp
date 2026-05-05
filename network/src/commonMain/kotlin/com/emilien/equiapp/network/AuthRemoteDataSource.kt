package com.emilien.equiapp.network

import com.emilien.equiapp.network.model.AuthResponse
import com.emilien.equiapp.network.model.LoginRequest
import com.emilien.equiapp.network.model.SignUpRequest
import com.emilien.equiapp.network.model.UserResponse
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.builtin.Email

interface AuthRemoteDataSource {
    suspend fun login(request: LoginRequest): AuthResponse
    suspend fun signUp(request: SignUpRequest): AuthResponse
}

class SupabaseAuthRemoteDataSource(
    private val auth: Auth
) : AuthRemoteDataSource {
    override suspend fun login(request: LoginRequest): AuthResponse {
        auth.signInWith(Email) {
            email = request.email
            password = request.password
        }
        val user = auth.currentUserOrNull() ?: throw Exception("Login failed")
        val session = auth.currentSessionOrNull() ?: throw Exception("No session found")
        
        return AuthResponse(
            accessToken = session.accessToken,
            refreshToken = session.refreshToken,
            user = UserResponse(
                id = user.id,
                email = user.email
            )
        )
    }

    override suspend fun signUp(request: SignUpRequest): AuthResponse {
        auth.signUpWith(Email) {
            email = request.email
            password = request.password
        }
        val user = auth.currentUserOrNull() ?: throw Exception("SignUp failed")
        val session = auth.currentSessionOrNull() ?: throw Exception("No session found")

        return AuthResponse(
            accessToken = session.accessToken,
            refreshToken = session.refreshToken,
            user = UserResponse(
                id = user.id,
                email = user.email
            )
        )
    }
}
