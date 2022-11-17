package com.example.recyclerview.ui.updateactivity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclerview.R
import com.example.recyclerview.data.DatabaseRoom
import com.example.recyclerview.data.Repository
import com.example.recyclerview.databinding.ActivityUpdateBinding
import com.example.recyclerview.domain.modelo.Persona
import com.example.recyclerview.domain.usecases.UpdatePersonaUseCase
import com.example.recyclerview.ui.common.ConstantesUI
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding

    private val viewModel: UpdateViewModel by viewModels {
        UpdateViewModel.UpdateViewModelFactory(
            UpdatePersonaUseCase(
                Repository(
                    DatabaseRoom.getDatabase(this).daoPersonas()
                )
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val nombre = intent.getStringExtra(ConstantesUI.NOMBRE)
        val email = intent.getStringExtra(ConstantesUI.EMAIL)
        val emailSinArroba = email?.substringBefore(ConstantesUI.ARROBA)
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
        if (binding.nameTextField.editText?.text.toString().isNotEmpty()) {
            val dialog = MaterialAlertDialogBuilder(this)
                .setTitle(R.string.update)
                .setMessage(R.string.update_message)
                .setPositiveButton(R.string.SI) { _, _ ->
                    val persona = Persona(
                        binding.nameTextField.editText?.text.toString(),
                        binding.passwordTextField.editText?.text.toString(),
                        binding.emailTextField.editText?.text.toString() + ConstantesUI.EMAIL_DOMAIN,
                    )
                    viewModel.handleEvent(UpdateEvent.UpdatePersona(persona))
                }
                .setNegativeButton(R.string.no) { _, _ -> }
                .create()
            dialog.show()
        } else {
            Toast.makeText(this, R.string.empty_fields, Toast.LENGTH_SHORT).show()
        }
    }
}