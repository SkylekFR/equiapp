package com.emilien.equiapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
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
                        HomeAsStudent()
                    }

                    is HomeAsTeacher -> NavEntry(key) {
                        HomeAsTeacher()

                    }
                    else -> NavEntry(Unit) {

                    }

                }
            }
        )

    }
}