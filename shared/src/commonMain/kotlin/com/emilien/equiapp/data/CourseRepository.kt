package com.emilien.equiapp.data

import com.emilien.equiapp.domain.Course
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    fun getCourse(courseId: String): Flow<Course?>
    suspend fun updatePresence(courseId: String, isPresent: Boolean, comment: String): Result<Unit>
}
