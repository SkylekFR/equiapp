package com.emilien.equiapp.auth.login

import com.emilien.equiapp.domain.auth.AuthError

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: AuthError? = null,
    val isSuccess: Boolean = false
)
