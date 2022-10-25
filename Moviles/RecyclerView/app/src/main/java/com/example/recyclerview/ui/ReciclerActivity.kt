package com.example.recyclerview.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.recyclerview.R
import com.example.recyclerview.domain.modelo.Personaje
import timber.log.Timber

class ReciclerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recicler)

        intent.extras?.let {
            val persona = it.getParcelable<Personaje>(getString(R.string.email))
            Timber.i("Nombre: $persona")
            Log.i("MITAG", "Nombre: $persona")
        }
    }
}