package com.emilien.equiapp.data.course

import com.emilien.equiapp.domain.AppResult
import com.emilien.equiapp.domain.course.*
import com.emilien.equiapp.network.CourseRemoteDataSource
import com.emilien.equiapp.network.model.CourseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CourseRepositoryImpl(
    private val dataSource: CourseRemoteDataSource
) : CourseRepository {

    override fun getCourse(courseId: String): Flow<Course?> = flow {
        try {
            val courses = dataSource.getCourses()
            emit(courses.find { it.id == courseId }?.toDomain())
        } catch (e: Exception) {
            emit(null)
        }
    }

    override fun getCourses(): Flow<AppResult<List<Course>, CourseError>> = flow {
        try {
            val dtos = dataSource.getCourses()
            emit(AppResult.Success(dtos.map { it.toDomain() }))
        } catch (e: Exception) {
            emit(AppResult.Failure(CourseError.Network.ServerError))
        }
    }

    override suspend fun updatePresence(
        courseId: String,
        isPresent: Boolean,
        comment: String
    ): AppResult<Unit, CourseError> {
        return try {
            dataSource.updatePresence(courseId, isPresent, comment)
            AppResult.Success(Unit)
        } catch (e: Exception) {
            AppResult.Failure(CourseError.Network.ServerError)
        }
    }

    private fun CourseDto.toDomain(): Course = Course(
        id = id,
        theme = title,
        teacher = teacherId,
        horse = "", 
        time = "", 
        startTimeMillis = 1000, 
        paymentStatus = "",
        credits = 2,
        students = emptyList(),
        comment = ""
    )
}
