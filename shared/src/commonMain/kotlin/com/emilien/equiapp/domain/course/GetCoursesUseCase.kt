package com.emilien.equiapp.domain.course

import com.emilien.equiapp.domain.AppResult

class GetCoursesUseCase(
    private val repository: CourseRepository
) {
    suspend operator fun invoke(): AppResult<List<Course>, CourseError> {
        return repository.getCourses()
    }
}
