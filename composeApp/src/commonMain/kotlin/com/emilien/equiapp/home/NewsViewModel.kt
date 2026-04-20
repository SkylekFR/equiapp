package com.emilien.equiapp.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.NewReleases
import androidx.compose.material.icons.filled.Warning
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emilien.equiapp.NewsMock
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(NewsUiState())
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    init {
        loadNews()
    }

    fun onEvent(event: NewsUiEvent) {
        when (event) {
            is NewsUiEvent.RefreshNews -> loadNews()
            is NewsUiEvent.OnNewsClick -> { /* Handle news click if needed */ }
        }
    }

    private fun loadNews() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                // Mock network delay
                // kotlinx.coroutines.delay(1000)
                
                val mockNews = listOf(
                    NewsMock("Thunder is here!", "Our new jumping star has arrived in the stables.", "2h ago", Icons.Default.NewReleases),
                    NewsMock("Regional Results", "Check out the podium of last weekend!", "1d ago", Icons.Default.EmojiEvents),
                    NewsMock("Maintenance", "The main arena will be closed on Friday morning.", "2d ago", Icons.Default.Warning)
                )
                
                _uiState.update { it.copy(news = mockNews, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = NewsError.UnknownError) }
            }
        }
    }
}
