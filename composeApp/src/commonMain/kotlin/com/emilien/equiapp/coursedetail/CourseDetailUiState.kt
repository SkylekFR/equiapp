package com.emilien.equiapp.coursedetail

data class CourseDetailUiState(
    val courseId: String = "",
    val theme: String = "",
    val teacher: String = "",
    val horse: String? = null,
    val time: String = "",
    val paymentStatus: String = "",
    val credits: Int = 0,
    val presenceConfirmed: Boolean? = null,
    val comment: String = "",
    val otherStudents: List<StudentMock> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
