package com.example.comunicate2

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.database.*

@Composable
fun SavedTextsScreen(navController: NavController) {
    var texts by remember { mutableStateOf<Map<String, String>>(emptyMap()) }
    var editedText by remember { mutableStateOf("") }
    var editingKey by remember { mutableStateOf<String?>(null) }

    val database = FirebaseDatabase.getInstance().getReference("textos")
    
    LaunchedEffect(Unit) {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                texts = snapshot.children.associate { it.key!! to it.getValue(String::class.java)!! }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Textos Guardados",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.weight(1f)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(texts.entries.toList()) { (key, text) ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            if (editingKey == key) {
                                OutlinedTextField(
                                    value = editedText,
                                    onValueChange = { editedText = it },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Button(
                                        onClick = {
                                            updateTextInFirebase(key, editedText)
                                            editingKey = null
                                        }
                                    ) {
                                        Text("Guardar")
                                    }

                                    Button(
                                        onClick = { editingKey = null }
                                    ) {
                                        Text("Cancelar")
                                    }
                                }
                            } else {
                                Text(
                                    text = text,
                                    style = MaterialTheme.typography.bodyLarge
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    IconButton(onClick = {
                                        editingKey = key
                                        editedText = text
                                    }) {
                                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar")
                                    }

                                    IconButton(onClick = { deleteTextFromFirebase(key) }) {
                                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate(Routes.HOME) },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Volver al Inicio", fontSize = 18.sp)
        }
    }
}

fun updateTextInFirebase(key: String, newText: String) {
    val database = FirebaseDatabase.getInstance().getReference("textos")
    database.child(key).setValue(newText)
}

fun deleteTextFromFirebase(key: String) {
    val database = FirebaseDatabase.getInstance().getReference("textos")
    database.child(key).removeValue()
}
