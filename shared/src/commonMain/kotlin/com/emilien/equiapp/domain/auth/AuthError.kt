package com.emilien.equiapp.domain.auth

sealed interface AuthError {
    sealed interface Network : AuthError {
        data object NoConnection : Network
        data object Timeout : Network
        data object ServerError : Network
    }
    sealed interface Business : AuthError {
        data object InvalidCredentials : Business
        data object EmailAlreadyExists : Business
        data object WeakPassword : Business
        data object Unauthorized : Business
    }
    data object Unknown : AuthError
}
