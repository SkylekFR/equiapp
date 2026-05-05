package com.emilien.equiapp.auth.login

sealed class LoginIntent {
    data class UpdateEmail(val email: String) : LoginIntent()
    data class UpdatePassword(val password: String) : LoginIntent()
    data object Submit : LoginIntent()
}
