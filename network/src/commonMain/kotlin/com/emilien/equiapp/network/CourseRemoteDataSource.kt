package com.emilien.equiapp.network

import com.emilien.equiapp.network.model.CourseDto
import io.github.jan.supabase.postgrest.Postgrest

interface CourseRemoteDataSource {
    suspend fun getCourses(): List<CourseDto>
    suspend fun updatePresence(courseId: String, isPresent: Boolean, comment: String)
}

class SupabaseCourseRemoteDataSource(
    private val postgrest: Postgrest
) : CourseRemoteDataSource {
    override suspend fun getCourses(): List<CourseDto> {
        return postgrest.from("course").select().decodeList<CourseDto>()
    }

    override suspend fun updatePresence(courseId: String, isPresent: Boolean, comment: String) {
        postgrest.from("course").update(
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
