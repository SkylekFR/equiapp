package com.emilien.equiapp.coursedetail

import com.emilien.equiapp.domain.AppResult
import com.emilien.equiapp.domain.course.CourseError
import com.emilien.equiapp.domain.course.CourseRepository

class DeclarePresenceUseCase(
    private val repository: CourseRepository
) {
    suspend fun execute(
        courseId: String,
        isPresent: Boolean,
        comment: String,
        courseStartTimeMillis: Long,
        currentTimeMillis: Long
    ): AppResult<Unit, CourseError> {
        if (!isPresent) {
            val twentyFourHoursInMillis = 24 * 60 * 60 * 1000L
            if (courseStartTimeMillis - currentTimeMillis < twentyFourHoursInMillis) {
                // Return a specific business error
                return AppResult.Failure(CourseError.Business.AccessDenied)
            }
        }
        return repository.updatePresence(courseId, isPresent, comment)
    }
}
