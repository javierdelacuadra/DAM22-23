package com.example.recyclerview.ui.old.updateactivity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclerview.R
import com.example.recyclerview.databinding.ActivityUpdateBinding
import com.example.recyclerview.domain.modelo.Persona
import com.example.recyclerview.ui.common.ConstantesUI
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding

    private val viewModel: UpdateViewModel by viewModels()

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
                    Timber.i(it)
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