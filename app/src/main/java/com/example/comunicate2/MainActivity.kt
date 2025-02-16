package com.example.comunicate2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.example.comunicate2.ui.theme.Comunicate2Theme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController



object Routes {
    const val SPLASH = "splash"
    const val HOME = "home"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val FORGOT_PASSWORD = "forgotPassword"
    const val MAP = "map"
    const val SPEECH_TO_TEXT = "speech_to_text"
    const val SAVED_TEXTS = "saved_texts"
    const val TEXT_TO_SPEECH = "text_to_speech"

}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Comunicate2Theme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {
        composable(Routes.SPLASH) {
            SplashScreen(navController)
        }
        composable(Routes.HOME) {
            HomeScreen(navController)
        }
        composable(Routes.LOGIN) {
            LoginScreen(navController)
        }
        composable(Routes.REGISTER) {
            RegisterScreen(navController)
        }
        composable(Routes.FORGOT_PASSWORD) {
            ForgotPasswordScreen(navController)
        }
        composable(Routes.MAP) {
            MapScreen(navController)
        }
        composable(Routes.SPEECH_TO_TEXT) {
            SpeechToTextScreen(navController)
        }
        composable(Routes.SAVED_TEXTS) {
            SavedTextsScreen(navController)
        }
        composable(Routes.TEXT_TO_SPEECH) {
            TextToSpeechScreen(navController)
        }

    }
}