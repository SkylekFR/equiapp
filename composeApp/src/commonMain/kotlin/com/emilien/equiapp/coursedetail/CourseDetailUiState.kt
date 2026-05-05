package com.emilien.equiapp.coursedetail

import com.emilien.equiapp.domain.course.CourseStudent

data class CourseDetailUiState(
    val courseId: String = "",
    val theme: String = "",
    val teacher: String = "",
    val horse: String? = null,
    val time: String = "",
    val courseStartTimeMillis: Long = 0L,
    val paymentStatus: String = "",
    val credits: Int = 0,
    val presenceConfirmed: Boolean? = null,
    val comment: String = "",
    val otherStudents: List<CourseStudent> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
