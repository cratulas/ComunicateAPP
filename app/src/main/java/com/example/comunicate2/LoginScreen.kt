package com.example.comunicate2

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AnimacionLottie()


        AppLogo(
            modifier = Modifier
                .padding(10.dp),
        )

        Text(
            text = "Inicio de Sesión",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier
            .height(24.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Usuario") },
            singleLine = true,
            placeholder = { Text("Ej. usuario1") },
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
            visualTransformation = PasswordVisualTransformation(),
            placeholder = { Text("Ingresa tu contraseña") },
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier
            .height(16.dp))

        Button(
            onClick = {
                if (username.isNotEmpty() && password.isNotEmpty()){
                    val userFound = UserData.users.any {
                        it.username == username && it.password == password
                    }
                    if (userFound) {
                        Toast.makeText(
                            context,
                            "Bienvenido $username",
                            Toast.LENGTH_SHORT
                        ).show()
                        navController.navigate(Routes.HOME)
                    } else {
                        errorMessage = "Usuario o contraseña incorrectos."
                    }

                }else {
                    errorMessage = "Usuario y contraseña no deben estar vacíos."
                }

            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text("Iniciar Sesión")
        }

        Spacer(modifier = Modifier
            .height(16.dp))

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier
                .height(8.dp))
        }

        Button(
            onClick = {
                navController.navigate(Routes.REGISTER)
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text("Registrarse")
        }

        Spacer(modifier = Modifier
            .height(16.dp))


        Button(
            onClick = {
                navController.navigate(Routes.FORGOT_PASSWORD)
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text("Olvidé mi contraseña")
        }

    }
}
