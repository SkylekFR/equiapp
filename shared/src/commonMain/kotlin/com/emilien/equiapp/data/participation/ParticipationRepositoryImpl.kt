package com.emilien.equiapp.data.participation

import com.emilien.equiapp.domain.AppResult
import com.emilien.equiapp.domain.course.CourseStudent
import com.emilien.equiapp.domain.course.CourseError
import com.emilien.equiapp.domain.participation.ParticipationRepository
import com.emilien.equiapp.network.CourseRemoteDataSource
import com.emilien.equiapp.network.model.ParticipationDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ParticipationRepositoryImpl(
    private val dataSource: CourseRemoteDataSource
) : ParticipationRepository {

    override fun getParticipationsForCourse(courseId: String): Flow<AppResult<List<CourseStudent>, CourseError>> = flow {
        try {
            val dtos = dataSource.getParticipations(courseId)
            emit(AppResult.Success(dtos.map { it.toDomain() }))

        } catch (e: Exception) {
            emit(AppResult.Failure(CourseError.Network.ServerError))
        }
    }

    private fun ParticipationDto.toDomain(): CourseStudent = CourseStudent(
        name =  "Unknown Student",
        horse = "",
        status = presenceStatus
    )
}
