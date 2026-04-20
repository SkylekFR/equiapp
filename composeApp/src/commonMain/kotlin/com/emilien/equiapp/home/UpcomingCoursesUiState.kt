package com.emilien.equiapp.home

import com.emilien.equiapp.CourseMock

data class UpcomingCoursesUiState(
    val courses: List<CourseMock> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
