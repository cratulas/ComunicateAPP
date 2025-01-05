package com.example.comunicate2

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ForgotPasswordScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Ingresa tu nombre de usuario") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier
            .height(16.dp))

        Button(
            onClick = {
                val user = UserData.users.find { it.username == username }
                if (user != null) {
                    message = "Tu contraseña es: ${user.password}"
                } else {
                    message = "Usuario no encontrado."
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text("Recuperar Contraseña")
        }

        Spacer(modifier = Modifier
            .height(16.dp))

        if (message.isNotEmpty()) {
            Text(text = message)
        }

        Spacer(modifier = Modifier
            .height(16.dp))

        Button(
            onClick = {
                navController.navigate(Routes.LOGIN)
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text("Volver al Login")
        }
    }
}
