package com.emilien.equiapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeAsStudent(){
    Scaffold { scaffoldPadding ->
        Box(
            modifier = Modifier.padding(scaffoldPadding)
        ) {
            Text("Home as student")
        }
    }
}