package com.emilien.equiapp.domain.auth

data class UserSession(
    val userId: String,
    val email: String,
    val accessToken: String,
    val refreshToken: String
)
