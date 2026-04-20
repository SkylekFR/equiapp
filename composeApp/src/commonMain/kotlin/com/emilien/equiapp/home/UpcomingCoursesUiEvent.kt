package com.emilien.equiapp.home

sealed class UpcomingCoursesUiEvent {
    data object RefreshCourses : UpcomingCoursesUiEvent()
    data class OnCourseClick(val courseId: String) : UpcomingCoursesUiEvent()
}
