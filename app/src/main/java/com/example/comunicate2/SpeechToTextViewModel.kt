package com.example.comunicate2

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.lifecycle.AndroidViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.google.firebase.database.FirebaseDatabase

class SpeechToTextViewModel(application: Application) : AndroidViewModel(application) {
    private val speechRecognizer: SpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(application)
    var recognizedText by mutableStateOf("Presiona el botón y habla")
        private set

    init {
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                recognizedText = "Listo para escuchar..."
            }

            override fun onBeginningOfSpeech() {
                recognizedText = "Escuchando..."
            }

            override fun onRmsChanged(rmsdB: Float) {}

            override fun onBufferReceived(buffer: ByteArray?) {}

            override fun onEndOfSpeech() {
                recognizedText = "Procesando..."
            }

            override fun onError(error: Int) {
                recognizedText = "Error al reconocer la voz"
            }

            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                recognizedText = matches?.firstOrNull() ?: "No se pudo reconocer el texto"
                saveTextToFirebase(recognizedText)
            }

            override fun onPartialResults(partialResults: Bundle?) {}

            override fun onEvent(eventType: Int, params: Bundle?) {}
        })
    }

    fun startListening() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES")
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Habla ahora...")
        }
        speechRecognizer.startListening(intent)
    }

    private fun saveTextToFirebase(text: String) {
        val database = FirebaseDatabase.getInstance().getReference("textos")
        val key = database.push().key
        key?.let {
            database.child(it).setValue(text)
        }
    }

    override fun onCleared() {
        super.onCleared()
        speechRecognizer.destroy()
    }
}
