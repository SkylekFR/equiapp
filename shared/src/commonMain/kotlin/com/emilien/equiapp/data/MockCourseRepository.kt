package com.emilien.equiapp.data

import com.emilien.equiapp.domain.Course
import com.emilien.equiapp.domain.CourseStudent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class MockCourseRepository : CourseRepository {

    private val courses = MutableStateFlow(
        listOf(
            Course(
                id = "1",
                theme = "Jumping Level 2",
                teacher = "Jean-Pierre",
                horse = "Thunder",
                time = "Tomorrow, 11:00",
                startTimeMillis = 1713715200000L + (25 * 60 * 60 * 1000L), // Tomorrow
                paymentStatus = "Paid (Annual)",
                credits = 1,
                students = listOf(
                    CourseStudent("Alice", "Spirit", "Present"),
                    CourseStudent("Bob", "Daisy", "Present"),
                    CourseStudent("Charlie", null, "Absent"),
                    CourseStudent("Diana", "Goldie", "Unknown")
                )
            ),
            Course(
                id = "2",
                theme = "Basic Dressage",
                teacher = "Jean-Pierre",
                horse = "Cloud",
                time = "Today, 12:00",
                startTimeMillis = 1713715200000L + (2 * 60 * 60 * 1000L), // Today soon
                paymentStatus = "Paid (Annual)",
                credits = 1,
                students = listOf(
                    CourseStudent("Alice", "Spirit", "Present"),
                    CourseStudent("Bob", "Daisy", "Present")
                )
            )
        )
    )

    override fun getCourse(courseId: String): Flow<Course?> {
        return courses.map { list -> list.find { it.id == courseId } }
    }

    override suspend fun updatePresence(courseId: String, isPresent: Boolean, comment: String): Result<Unit> {
        delay(1000) // Simulate network
        courses.update { list ->
            list.map { course ->
                if (course.id == courseId) {
                    course.copy(presenceConfirmed = isPresent, comment = comment)
                } else {
                    course
                }
            }
        }
        return Result.success(Unit)
    }
}
