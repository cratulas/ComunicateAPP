package com.example.comunicate2

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedTextField
import android.widget.Toast
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext


@Composable
fun RegisterScreen(navController: NavController) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmationMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Registro de Usuario",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier
            .height(24.dp))


        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Nombre de Usuario") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier
            .height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier
            .height(16.dp))

        Button(
            onClick = {
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    UserData.users.add(User(username, password))
                    // Mensaje de exito
                    Toast.makeText(
                        context,
                        "Usuario $username registrado con éxito.",
                        Toast.LENGTH_SHORT
                    ).show()

                    navController.navigate(Routes.LOGIN)
                } else {
                    confirmationMessage = "Usuario y contraseña no deben estar vacíos."
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Registrarse")
        }

        Spacer(modifier = Modifier
            .height(16.dp))

        if (confirmationMessage.isNotEmpty()) {
            Text(text = confirmationMessage)
        }


        Button(
            onClick = {
                navController.navigate(Routes.LOGIN)
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Volver al Inicio de Sesión")
        }
    }
}

