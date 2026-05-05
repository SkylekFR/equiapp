package com.emilien.equiapp.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AutoGraph
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emilien.equiapp.CourseMock
import com.emilien.equiapp.domain.AppResult
import com.emilien.equiapp.domain.course.GetCoursesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UpcomingCoursesViewModel(
    private val getCoursesUseCase: GetCoursesUseCase
) : ViewModel() {

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
            
            when (val result = getCoursesUseCase()) {
                is AppResult.Success -> {
                    val courses = result.data.map { course ->
                        CourseMock(
                            id = course.id,
                            title = course.theme ?: "",
                            time = course.time,
                            location = "Main Arena", // Default for now
                            icon = if (course.theme?.contains("Jumping") == true) Icons.AutoMirrored.Filled.TrendingUp else Icons.Default.AutoGraph
                        )
                    }
                    _uiState.update { it.copy(courses = courses, isLoading = false) }
                }
                is AppResult.Failure -> {
                    _uiState.update { it.copy(isLoading = false, error = "Failed to load courses") }
                }
            }
        }
    }
}
