package com.emilien.equiapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HomeAsStudent(){
    Scaffold { scaffoldPadding ->
        Box(
            modifier = Modifier.padding(scaffoldPadding)
        ) {
            Text("Home as st@udent")
        }
    }
}

@Preview
@Composable
fun HomeAsStudentPreview() {
    HomeAsStudent()
}