package com.emilien.equiapp.coursedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CourseDetailViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CourseDetailUiState())
    val uiState: StateFlow<CourseDetailUiState> = _uiState.asStateFlow()

    fun onEvent(event: CourseDetailUiEvent) {
        when (event) {
            is CourseDetailUiEvent.LoadCourse -> loadCourse(event.courseId)
            is CourseDetailUiEvent.ConfirmPresence -> {
                _uiState.update { it.copy(presenceConfirmed = event.confirmed) }
            }
            is CourseDetailUiEvent.UpdateComment -> {
                _uiState.update { it.copy(comment = event.comment) }
            }
        }
    }

    private fun loadCourse(courseId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                // Mock loading based on ID
                val mockStudents = listOf(
                    StudentMock("Alice", "Spirit", "Present"),
                    StudentMock("Bob", "Daisy", "Present"),
                    StudentMock("Charlie", null, "Absent"),
                    StudentMock("Diana", "Goldie", "Unknown")
                )

                _uiState.update {
                    it.copy(
                        courseId = courseId,
                        theme = if (courseId == "1") "Jumping Level 2" else "Basic Dressage",
                        teacher = "Jean-Pierre",
                        horse = if (courseId == "1") "Thunder" else "Cloud",
                        time = if (courseId == "1") "Tomorrow, 10:00 - 11:30" else "Wed, 14:00 - 15:30",
                        paymentStatus = "Paid (Annual)",
                        credits = 1,
                        otherStudents = mockStudents,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = "Failed to load course details") }
            }
        }
    }
}
