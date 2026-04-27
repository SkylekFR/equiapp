package com.emilien.equiapp.coursedetail

sealed class CourseDetailUiEvent {
    data class LoadCourse(val courseId: String) : CourseDetailUiEvent()
    data class ConfirmPresence(val confirmed: Boolean) : CourseDetailUiEvent()
    data class UpdateComment(val comment: String) : CourseDetailUiEvent()
}
