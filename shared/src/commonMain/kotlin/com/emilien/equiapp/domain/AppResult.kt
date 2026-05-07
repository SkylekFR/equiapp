package com.emilien.equiapp.domain

sealed interface AppResult<out T, out E> {
    data class Success<T>(val data: T) : AppResult<T, Nothing>
    data class Failure<E>(val error: E) : AppResult<Nothing, E>
}

inline fun <T, E, R> AppResult<T, E>.map(transform: (T) -> R): AppResult<R, E> {
    return when (this) {
        is AppResult.Success -> AppResult.Success(transform(data))
        is AppResult.Failure -> this
    }
}

inline fun <T, E, R> AppResult<T, E>.mapError(transform: (E) -> R): AppResult<T, R> {
    return when (this) {
        is AppResult.Success -> this
        is AppResult.Failure -> AppResult.Failure(transform(error))
    }
}

inline fun <T, E> AppResult<T, E>.onSuccess(action: (T) -> Unit): AppResult<T, E> {
    if (this is AppResult.Success) {
        action(data)
    }
    return this
}

inline fun <T, E> AppResult<T, E>.onFailure(action: (E) -> Unit): AppResult<T, E> {
    if (this is AppResult.Failure) {
        action(error)
    }
    return this
}

inline fun <T, E, R> AppResult<T, E>.fold(
    onSuccess: (T) -> R,
    onFailure: (E) -> R
): R {
    return when (this) {
        is AppResult.Success -> onSuccess(data)
        is AppResult.Failure -> onFailure(error)
    }
}
