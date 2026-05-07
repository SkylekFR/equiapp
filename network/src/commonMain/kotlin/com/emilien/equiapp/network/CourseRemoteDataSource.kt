package com.emilien.equiapp.network

import com.emilien.equiapp.network.model.CourseDto
import com.emilien.equiapp.network.model.ParticipationDto
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns

interface CourseRemoteDataSource {
    suspend fun getCourses(): List<CourseDto>
    suspend fun getParticipations(courseId: String): List<ParticipationDto>
    suspend fun updatePresence(courseId: String, isPresent: Boolean, comment: String)
}

class SupabaseCourseRemoteDataSource(
    private val postgrest: Postgrest
) : CourseRemoteDataSource {
    override suspend fun getCourses(): List<CourseDto> {
        return postgrest.from("cours")
            .select()
            .decodeList<CourseDto>()
    }

    override suspend fun getParticipations(courseId: String): List<ParticipationDto> {
        return postgrest.from("participation")
            .select {
                filter {
                    eq("cours_id", courseId)
                }
            }
            .decodeList<ParticipationDto>()
    }

    override suspend fun updatePresence(courseId: String, isPresent: Boolean, comment: String) {
        postgrest.from("cours").update(
            {
                set("presence_confirmed", isPresent)
                set("comment", comment)
            }
        ) {
            filter {
                eq("id", courseId)
            }
        }
    }
}
