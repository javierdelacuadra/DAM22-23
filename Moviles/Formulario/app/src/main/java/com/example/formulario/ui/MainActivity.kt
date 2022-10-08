package com.example.formulario.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.formulario.databinding.ActivityMainBinding
import com.example.formulario.domain.modelo.Persona
import com.example.formulario.domain.usecases.AddPersonaUseCase
import com.example.formulario.domain.usecases.DeletePersonaUseCase
import com.example.formulario.domain.usecases.GetPersonasUseCase
import com.example.formulario.utils.StringProvider

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            StringProvider.instance(this),
            AddPersonaUseCase(),
            GetPersonasUseCase(),
            DeletePersonaUseCase(),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
            addButton.setOnClickListener {
                addPersona()
            }
            deleteButton.setOnClickListener {
                deletePersona()
            }
            anteriorButton.setOnClickListener {
                anteriorPersona()
            }
            siguienteButton.setOnClickListener {
                siguientePersona()
            }
            viewModel.uiState.observe(this@MainActivity) { uiState ->
                uiState.persona.let {
                    nameTextField.editText?.setText(it.nombre)
                    passwordTextField.editText?.setText(it.password)
                    emailTextField.editText?.setText(it.email.substringBefore(Constantes.ARROBA))
                    switchMaterial.isChecked = it.notificaciones
                    labelPersonas.text = it.id.toString()
                }
                uiState.mensaje?.let {
                    Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun addPersona() {
        val persona = Persona(
            GetPersonasUseCase().invoke().size + Constantes.UNO,
            binding.nameTextField.editText?.text.toString(),
            binding.passwordTextField.editText?.text.toString(),
            binding.emailTextField.editText?.text.toString() + Constantes.EMAIL_DOMAIN,
            binding.switchMaterial.isChecked
        )
        viewModel.addPersona(persona)
    }

    private fun deletePersona() {
        val persona = Persona(
            binding.labelPersonas.text.toString().toInt(),
            binding.nameTextField.editText?.text.toString(),
            binding.passwordTextField.editText?.text.toString(),
            binding.emailTextField.editText?.text.toString() + Constantes.EMAIL_DOMAIN,
            binding.switchMaterial.isChecked
        )
        viewModel.deletePersona(persona)
    }

    private fun anteriorPersona() {
        val id = binding.labelPersonas.text.toString().toInt()
        viewModel.getPersonaAnterior(id)
    }

    private fun siguientePersona() {
        val id = binding.labelPersonas.text.toString().toInt()
        viewModel.getPersonaSiguiente(id)
    }
}