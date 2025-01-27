package com.example.comunicate2.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color

// 🎨 Definimos la nueva paleta de colores accesible
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF00796B),  // Verde Teal Oscuro
    primaryContainer = Color(0xFF004D40), // Variante más oscura
    secondary = Color(0xFF4DB6AC),  // Verde Teal Claro
    background = Color(0xFF121212), // Fondo oscuro accesible
    surface = Color(0xFF1E1E1E), // Superficie oscura para tarjetas
    error = Color(0xFFD32F2F), // Rojo accesible para errores
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF00796B),  // Verde Teal Oscuro
    primaryContainer = Color(0xFF004D40), // Variante más oscura
    secondary = Color(0xFF4DB6AC),  // Verde Teal Claro
    background = Color(0xFFE0F2F1), // Fondo claro
    surface = Color.White, // Superficie clara
    error = Color(0xFFD32F2F), // Rojo accesible para errores
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun Comunicate2Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
