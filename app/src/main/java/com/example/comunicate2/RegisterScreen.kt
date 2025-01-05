package com.example.comunicate2

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import android.util.Patterns


@Composable
fun RegisterScreen(navController: NavController) {
    val context = LocalContext.current

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var confirmationMessage by remember { mutableStateOf("") }

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
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
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
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()

        )

        Spacer(modifier = Modifier
            .height(16.dp))


        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmar Contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier
            .height(16.dp))


        Button(
            onClick = {
                if (username.isNotEmpty() && email.isNotEmpty()
                    && password.isNotEmpty() && confirmPassword.isNotEmpty()
                ) {
                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        confirmationMessage = "Formato de correo inválido. Por favor revisa tu email."
                    } else if (password != confirmPassword) {
                        confirmationMessage = "Las contraseñas no coinciden."
                    } else {
                        val existingUser = UserData.users.find { it.username == username }
                        if (existingUser != null) {
                            confirmationMessage = "El usuario '$username' ya existe. Intenta con otro."
                        } else {

                            UserData.users.add(User(username, password, email))
                            Toast.makeText(
                                context,
                                "$username registrado con éxito.",
                                Toast.LENGTH_SHORT
                            ).show()

                            navController.navigate(Routes.LOGIN)
                        }
                    }
                } else {
                    confirmationMessage = "Todos los campos son obligatorios."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Registrarse")
        }


        Spacer(modifier = Modifier
            .height(16.dp))


        if (confirmationMessage.isNotEmpty()) {
            Text(
                text = confirmationMessage,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier
                .height(8.dp))
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
