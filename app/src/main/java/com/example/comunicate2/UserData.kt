package com.example.comunicate2

data class User(
    val username: String,
    val password: String
)

object UserData {
    val users = mutableListOf(
        User("usuario1", "1234"),
        User("usuario2", "abcd"),
        User("usuario3", "admin"),
        User("usuario4", "5678"),
        User("usuario5", "hola"),
    )
}
