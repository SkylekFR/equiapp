package com.emilien.equiapp.domain.course

data class Course(
    val id: String,
    val theme: String?,
    val teacher: String,
    val horse: String?,
    val time: String,
    val startTimeMillis: Long,
    val paymentStatus: String,
    val credits: Int,
    val students: List<CourseStudent>,
    val comment: String = ""
)

data class CourseStudent(
    val name: String,
    val horse: String?,
    val status: String
)
