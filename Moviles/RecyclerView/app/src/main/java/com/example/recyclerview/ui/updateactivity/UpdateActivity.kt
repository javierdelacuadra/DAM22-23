package com.example.recyclerview.ui.updateactivity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclerview.R
import com.example.recyclerview.databinding.ActivityUpdateBinding
import com.example.recyclerview.domain.modelo.Persona
import com.example.recyclerview.domain.usecases.UpdatePersonaUseCase
import com.example.recyclerview.ui.common.Constantes
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding

    private val viewModel: UpdateViewModel by viewModels {
        UpdateViewModel.UpdateViewModelFactory(
            UpdatePersonaUseCase()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val nombre = intent.getStringExtra(Constantes.NOMBRE)
        val email = intent.getStringExtra(Constantes.EMAIL)
        val emailSinArroba = email?.substringBefore(Constantes.ARROBA)
        binding = ActivityUpdateBinding.inflate(layoutInflater).apply {
            setContentView(root)
            nameTextField.editText?.setText(nombre)
            emailTextField.editText?.setText(emailSinArroba)
            viewModel.state.observe(this@UpdateActivity) { uiState ->
                uiState.mensaje?.let {
                    Toast.makeText(this@UpdateActivity, it, Toast.LENGTH_SHORT).show()
                }
            }
            updateButton.setOnClickListener {
                updatePersona()
            }
        }
    }

    private fun updatePersona() {
        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle(R.string.update)
            .setMessage(R.string.update_message)
            .setPositiveButton(R.string.SI) { _, _ ->
                viewModel.updatePersona(
                    Persona(
                        binding.nameTextField.editText?.text.toString(),
                        binding.passwordTextField.editText?.text.toString(),
                        binding.emailTextField.editText?.text.toString() + Constantes.EMAIL_DOMAIN,
                    )
                )
            }
            .setNegativeButton(R.string.no) { _, _ -> }
            .create()
        dialog.show()
    }
}