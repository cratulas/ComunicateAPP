package com.example.comunicate2

import android.app.Application
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import java.util.Locale

class TextToSpeechViewModel(application: Application) : AndroidViewModel(application), TextToSpeech.OnInitListener {

    private var textToSpeech: TextToSpeech? = TextToSpeech(application, this)

    private val _isInitialized = MutableLiveData(false)

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech?.setLanguage(Locale("es", "ES")) //
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Idioma no soportado")
            } else {
                _isInitialized.value = true
            }
        } else {
            Log.e("TTS", "Inicialización fallida")
        }
    }

    fun speakText(text: String) {
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    override fun onCleared() {
        textToSpeech?.stop()
        textToSpeech?.shutdown()
        super.onCleared()
    }
}
