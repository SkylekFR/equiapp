package com.emilien.equiapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.emilien.equiapp.coursedetail.CourseDetailScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    EquiAppTheme {
        val backStack = remember { mutableStateListOf<Any>(Login) }
        NavDisplay(
            backStack = backStack,
            onBack = {
                backStack.removeLastOrNull()
            },
            entryProvider = { key ->
                when (key) {
                    is Login -> NavEntry(key) {
                        LoginScreen(
                            onClickLoginAsStudent = {
                                backStack.add(HomeAsStudent)
                            },
                            onClickLoginAsTeacher = {
                                backStack.add(HomeAsTeacher)
                            }
                        )
                    }

                    is HomeAsStudent -> NavEntry(key) {
                        HomeAsStudent(
                            onNavigateToProfile = { backStack.add(Profile) },
                            onNavigateToCourses = { backStack.add(Courses) },
                            onNavigateToHorses = { backStack.add(Horses) },
                            onNavigateToHorsery = { backStack.add(Horsery) },
                            onNavigateToParameters = { backStack.add(Parameters) },
                            onNavigateToCourseDetail = { courseId -> backStack.add(CourseDetail(courseId)) }
                        )
                    }

                    is CourseDetail -> NavEntry(key) {
                        CourseDetailScreen(
                            courseId = key.courseId,
                            onBack = { backStack.removeLast() }
                        )
                    }

                    is HomeAsTeacher -> NavEntry(key) {
                        HomeAsTeacher()

                    }

                    is Profile -> NavEntry(key) {
                        Scaffold { Text("Profile Screen", modifier = Modifier.padding(it)) }
                    }

                    is Courses -> NavEntry(key) {
                        Scaffold { Text("Courses Screen", modifier = Modifier.padding(it)) }
                    }

                    is Horses -> NavEntry(key) {
                        Scaffold { Text("Horses Screen", modifier = Modifier.padding(it)) }
                    }

                    is Horsery -> NavEntry(key) {
                        Scaffold { Text("Horsery Screen", modifier = Modifier.padding(it)) }
                    }

                    is Parameters -> NavEntry(key) {
                        Scaffold { Text("Parameters Screen", modifier = Modifier.padding(it)) }
                    }
                    else -> NavEntry(Unit) {

                    }

                }
            }
        )

    }
}