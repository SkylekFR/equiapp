package com.emilien.equiapp.domain

sealed interface AppResult<out T, out E> {
    data class Success<T>(val data: T) : AppResult<T, Nothing>
    data class Failure<E>(val error: E) : AppResult<Nothing, E>
}
