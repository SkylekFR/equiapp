package com.emilien.equiapp.home

import com.emilien.equiapp.CourseUiModel

data class UpcomingCoursesUiState(
    val courses: List<CourseUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
