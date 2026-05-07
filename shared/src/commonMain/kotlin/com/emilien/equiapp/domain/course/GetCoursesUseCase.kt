package com.emilien.equiapp.domain.course

import com.emilien.equiapp.domain.AppResult
import com.emilien.equiapp.domain.participation.ParticipationRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class GetCoursesUseCase(
    private val repository: CourseRepository,
    private val participationRepository: ParticipationRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<AppResult<List<Course>, CourseError>> {
        return repository.getCourses().flatMapLatest { result ->
            when (result) {
                is AppResult.Failure -> flowOf(result)
                is AppResult.Success -> {
                    val courses = result.data
                    if (courses.isEmpty()) return@flatMapLatest flowOf(result)

                    val participationFlows = courses.map { course ->
                        participationRepository.getParticipationsForCourse(course.id)
                            .map { partResult -> course to partResult }
                    }

                    combine(participationFlows) { pairings ->
                        val updatedCourses = pairings.map { (course, partResult) ->
                            if (partResult is AppResult.Success) {
                                course.copy(students = partResult.data)
                            } else {
                                course
                            }
                        }
                        AppResult.Success(updatedCourses)
                    }.onStart { emit(AppResult.Success(courses)) }
                }
            }
        }
    }
}
