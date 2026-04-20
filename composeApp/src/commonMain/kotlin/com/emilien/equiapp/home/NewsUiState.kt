package com.emilien.equiapp.home

import com.emilien.equiapp.NewsMock

data class NewsUiState(
    val news: List<NewsMock> = emptyList(),
    val isLoading: Boolean = false,
    val error: NewsError? = null
)

sealed class NewsError {
    data object NetworkError : NewsError()
    data object UnknownError : NewsError()
}
