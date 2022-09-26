package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {

    lateinit var cajaNombre: EditText
    lateinit var cajaPassword: EditText
    lateinit var botonLogin: Button
    lateinit var passwordSwitch: SwitchMaterial
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cajaNombre = findViewById(R.id.cajaNombre)
        cajaPassword = findViewById(R.id.cajaPassword)
        botonLogin = findViewById(R.id.botonLogin)
        passwordSwitch = findViewById(R.id.passwordSwitch)

        passwordSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "Tu contraseña será recordada", Toast.LENGTH_SHORT).show()
            }
        }

        botonLogin.setOnClickListener {
            val nombre = cajaNombre.text.toString()
            val password = cajaPassword.text.toString()
            if (nombre == "admin") {
                Toast.makeText(this, "Eres el admin", Toast.LENGTH_SHORT).show()
            } else if (nombre == password) {
                Toast.makeText(this, "El nombre es igual a la contraseña", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Un saludo $nombre", Toast.LENGTH_SHORT).show()
            }
        }
    }
}