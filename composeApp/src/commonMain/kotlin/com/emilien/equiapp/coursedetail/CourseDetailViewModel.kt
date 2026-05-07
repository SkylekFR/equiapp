package com.emilien.equiapp.coursedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emilien.equiapp.domain.course.CourseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CourseDetailViewModel(
    private val repository: CourseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CourseDetailUiState())
    val uiState: StateFlow<CourseDetailUiState> = _uiState.asStateFlow()

    fun onEvent(event: CourseDetailUiEvent) {
        when (event) {
            is CourseDetailUiEvent.LoadCourse -> loadCourse(event.courseId)
            else -> {} // Presence events handled by PresenceViewModel
        }
    }

    private fun loadCourse(courseId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            repository.getCourse(courseId).collectLatest { course ->
                if (course != null) {
                    _uiState.update {
                        it.copy(
                            courseId = course.id,
                            theme = course.theme ?: "",
                            teacher = course.teacher,
                            horse = course.horse,
                            time = course.time,
                            courseStartTimeMillis = course.startTimeMillis,
                            paymentStatus = course.paymentStatus,
                            credits = course.credits,
                            otherStudents = course.students,
                            comment = course.comment,
                            isLoading = false
                        )
                    }
                } else {
                    _uiState.update { it.copy(isLoading = false, error = "Course not found") }
                }
            }
        }
    }
}
