package com.emilien.equiapp.domain.course

import com.emilien.equiapp.domain.AppResult
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    fun getCourse(courseId: String): Flow<Course?>
    fun getCourses(): Flow<AppResult<List<Course>, CourseError>>
    suspend fun updatePresence(courseId: String, isPresent: Boolean, comment: String): AppResult<Unit, CourseError>
}
