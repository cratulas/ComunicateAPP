package com.example.comunicate2

data class User(
    val username: String,
    val password: String,
    val email: String
)

object UserData {
    val users = mutableListOf(
        User("usuario1", "1234", "correo1@gmail.com"),
        User("usuario2", "abcd", "correo2@gmail.com"),
        User("usuario3", "efgh", "correo2@gmail.com"),
        User("usuario4", "5678", "correo2@gmail.com"),
        User("usuario5", "hola", "correo3@gmail.com"),
    )
}
