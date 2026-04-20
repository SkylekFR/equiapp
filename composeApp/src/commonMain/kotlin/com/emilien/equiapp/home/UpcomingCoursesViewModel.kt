package com.emilien.equiapp.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AutoGraph
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emilien.equiapp.CourseMock
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UpcomingCoursesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UpcomingCoursesUiState())
    val uiState: StateFlow<UpcomingCoursesUiState> = _uiState.asStateFlow()

    init {
        loadCourses()
    }

    fun onEvent(event: UpcomingCoursesUiEvent) {
        when (event) {
            is UpcomingCoursesUiEvent.RefreshCourses -> loadCourses()
            is UpcomingCoursesUiEvent.OnCourseClick -> { /* Handle course click if needed */ }
        }
    }

    private fun loadCourses() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                // Mock network delay
                // kotlinx.coroutines.delay(800)
                
                val mockCourses = listOf(
                    CourseMock("1", "Jumping Level 2", "Tomorrow, 10:00", "Main Arena", Icons.AutoMirrored.Filled.TrendingUp),
                    CourseMock("2", "Basic Dressage", "Wed, 14:00", "Indoor Ring", Icons.Default.AutoGraph)
                )
                
                _uiState.update { it.copy(courses = mockCourses, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = "Failed to load courses") }
            }
        }
    }
}
