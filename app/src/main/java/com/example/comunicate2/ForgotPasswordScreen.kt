package com.example.comunicate2

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ForgotPasswordScreen(navController: NavController) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppLogo(
            modifier = Modifier
                .padding(16.dp),
        )

        Text(
            text = "Olvidaste tu contraseña?",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier
            .height(24.dp))


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
                if (username.isNotEmpty()
                ){
                    val user = UserData.users.find { it.username == username }
                    if (user != null) {
                        Toast.makeText(
                            context,
                            "Las instrucciones para restablecer tu contraseña fueron enviadas a tu correo",
                            Toast.LENGTH_SHORT
                        ).show()

                        navController.navigate(Routes.LOGIN)
                    } else {
                        message = "Usuario no encontrado."
                    }
                }else{
                    message = "Ingresa un nombre de usuario."
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
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier
                .height(8.dp))
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
            Text("Volver al Inicio de Sesión")
        }
    }
}
