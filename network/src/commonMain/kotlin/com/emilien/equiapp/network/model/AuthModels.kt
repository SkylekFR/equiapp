package com.emilien.equiapp.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    @SerialName("access_token") val accessToken: String,
    @SerialName("refresh_token") val refreshToken: String,
    @SerialName("user") val user: UserResponse
)

@Serializable
data class UserResponse(
    @SerialName("id") val id: String,
    @SerialName("email") val email: String? = null
)

@Serializable
data class LoginRequest(
    @SerialName("email") val email: String,
    @SerialName("password") val password: String
)

@Serializable
data class SignUpRequest(
    @SerialName("email") val email: String,
    @SerialName("password") val password: String,
    @SerialName("data") val data: Map<String, String>? = null
)
