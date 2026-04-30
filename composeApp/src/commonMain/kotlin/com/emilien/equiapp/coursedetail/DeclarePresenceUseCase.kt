package com.emilien.equiapp.coursedetail

import com.emilien.equiapp.data.CourseRepository

class DeclarePresenceUseCase(
    private val repository: CourseRepository
) {
    suspend fun execute(
        courseId: String,
        isPresent: Boolean,
        comment: String,
        courseStartTimeMillis: Long,
        currentTimeMillis: Long
    ): Result<Unit> {
        if (!isPresent) {
            val twentyFourHoursInMillis = 24 * 60 * 60 * 1000L
            if (courseStartTimeMillis - currentTimeMillis < twentyFourHoursInMillis) {
                return Result.failure(Exception("Absence must be declared at least 24h before. Fees may apply."))
            }
        }
        return repository.updatePresence(courseId, isPresent, comment)
    }
}
