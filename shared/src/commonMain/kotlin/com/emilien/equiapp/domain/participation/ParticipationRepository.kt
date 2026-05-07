package com.emilien.equiapp.domain.participation

import com.emilien.equiapp.domain.AppResult
import com.emilien.equiapp.domain.course.CourseStudent
import com.emilien.equiapp.domain.course.CourseError
import kotlinx.coroutines.flow.Flow

interface ParticipationRepository {
    fun getParticipationsForCourse(courseId: String): Flow<AppResult<List<CourseStudent>, CourseError>>
}
