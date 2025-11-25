package com.emilien.equiapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoginScreen(
    onClickLoginAsStudent: () -> Unit,
    onClickLoginAsTeacher: () -> Unit
) {
    Scaffold { scaffoldPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text("Login screen")
            Column {
                Button(
                    onClick = onClickLoginAsStudent
                ) {
                    Text("Se connecter en tant que cavalier")
                }
                Button(
                    onClick = onClickLoginAsTeacher
                ) {
                    Text("Se connecter en tant que moniteur")
                }
            }
        }
    }

}