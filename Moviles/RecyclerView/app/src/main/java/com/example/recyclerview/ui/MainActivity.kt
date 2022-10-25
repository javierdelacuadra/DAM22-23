package com.example.recyclerview.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.recyclerview.R
import com.example.recyclerview.databinding.ActivityMainBinding
import com.example.recyclerview.domain.modelo.Persona
import com.example.recyclerview.domain.modelo.Personaje
import com.example.recyclerview.domain.usecases.AddPersonaUseCase
import com.example.recyclerview.domain.usecases.AddPersonajeUseCase
import com.example.recyclerview.domain.usecases.DeletePersonaUseCase
import com.example.recyclerview.domain.usecases.GetPersonasUseCase
import com.example.recyclerview.utils.StringProvider
import com.example.recyclerview.ui.MainViewModel.MainViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            StringProvider.instance(this),
            AddPersonaUseCase(),
            GetPersonasUseCase(),
            DeletePersonaUseCase(),
            AddPersonajeUseCase(),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
            addButton.setOnClickListener {
                addPersona()
            }
            viewModel.uiState.observe(this@MainActivity) { uiState ->
                uiState.persona.let {
                    nameTextField.editText?.setText(it.nombre)
                    passwordTextField.editText?.setText(it.password)
                    emailTextField.editText?.setText(it.email.substringBefore(Constantes.ARROBA))
                }
                uiState.mensaje?.let {
                    Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
                }
            }
            imageView?.load(Uri.parse(Constantes.URL)) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_background)
                transformations(coil.transform.CircleCropTransformation())
            }
        }
    }

    private fun addPersona() {
        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle("Agregar persona")
            .setMessage("¿Desea agregar una persona?")
            .setPositiveButton("Si") { _, _ ->
                val persona = Persona(
                    GetPersonasUseCase().invoke().size + Constantes.UNO,
                    binding.nameTextField.editText?.text.toString(),
                    binding.passwordTextField.editText?.text.toString(),
                    binding.emailTextField.editText?.text.toString() + Constantes.ARROBA,
                    false
                )
                AddPersonaUseCase().invoke(persona)
                val intent = Intent(this, ReciclerActivity::class.java)
                intent.putExtra("id", 1)

                intent.putExtra("nombre", persona.nombre)
                startActivity(intent)
                Toast.makeText(this, "Persona agregada", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") { _, _ ->
                Toast.makeText(this, "Persona no agregada", Toast.LENGTH_SHORT).show()
            }
            .setCancelable(false)
            .create()

        dialog.show()
        val personaje = Personaje(
            binding.nameTextField.editText?.text.toString(),
            5, 5
//            binding.passwordTextField.editText?.text.toString(),
//            binding.emailTextField.editText?.text.toString())
        )
        viewModel.addPersonaje(personaje)
    }

}