package com.emilien.equiapp.home

sealed class NewsUiEvent {
    data object RefreshNews : NewsUiEvent()
    data class OnNewsClick(val newsId: String) : NewsUiEvent()
}
