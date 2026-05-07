package com.emilien.equiapp.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AutoGraph
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emilien.equiapp.CourseUiModel
import com.emilien.equiapp.domain.AppResult
import com.emilien.equiapp.domain.course.GetCoursesUseCase
import kotlinx.coroutines.flow.*

class UpcomingCoursesViewModel(
    private val getCoursesUseCase: GetCoursesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UpcomingCoursesUiState())
    val uiState: StateFlow<UpcomingCoursesUiState> = getCoursesUseCase().map { result ->
        return@map when (result) {
            is AppResult.Success -> {
                val courses = result.data.map { course ->
                    CourseUiModel(
                        id = course.id,
                        title = course.theme ?: "",
                        time = course.time,
                        location = "Main Arena", // Default for now
                        icon = if (course.theme?.contains("Jumping") == true) Icons.AutoMirrored.Filled.TrendingUp else Icons.Default.AutoGraph,
                        presenceCount = course.students.count { it.status == "present" },
                        totalPotentialCount = course.students.size
                    )
                }
                UpcomingCoursesUiState(
                    courses,
                    isLoading = false
                )
            }

            is AppResult.Failure -> {
                UpcomingCoursesUiState(
                    error = "Failed to load courses",
                    isLoading = false
                )
            }
        }
    }

        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UpcomingCoursesUiState())


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
        _uiState.update { it.copy(isLoading = true, error = null) }
        

    }
}
