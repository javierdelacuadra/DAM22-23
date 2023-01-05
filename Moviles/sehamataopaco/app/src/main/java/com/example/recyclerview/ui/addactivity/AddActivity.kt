package com.example.recyclerview.ui.addactivity

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.recyclerview.R
import com.example.recyclerview.databinding.ActivityAddBinding
import com.example.recyclerview.domain.modelo.Persona
import com.example.recyclerview.ui.common.ConstantesUI
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding

    private val viewModel: AddViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater).apply {
            setContentView(root)
            addButton.setOnClickListener {
                addPersona()
            }
            viewModel.uiState.observe(this@AddActivity) { uiState ->
                uiState.persona.let {
                    nameTextField.editText?.setText(it.nombre)
                    passwordTextField.editText?.setText(it.password)
                    emailTextField.editText?.setText(it.email)
                }
                uiState.mensaje?.let {
                    Timber.i(it)
                    Toast.makeText(this@AddActivity, it, Toast.LENGTH_SHORT).show()
                }
            }
            imageView.load(Uri.parse(ConstantesUI.URL)) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_background)
                transformations(coil.transform.CircleCropTransformation())
            }
        }
    }

    private fun addPersona() {
        if (binding.nameTextField.editText?.text.toString().isNotEmpty() &&
            binding.passwordTextField.editText?.text.toString().isNotEmpty() &&
            binding.emailTextField.editText?.text.toString().isNotEmpty()
        ) {
            val dialog = MaterialAlertDialogBuilder(this)
                .setTitle(ConstantesUI.AGREGAR_PERSONA)
                .setMessage(ConstantesUI.DESEA_AGREGAR_UNA_PERSONA)
                .setPositiveButton(R.string.SI) { _, _ ->
                    val persona = Persona(
                        binding.nameTextField.editText?.text.toString(),
                        binding.passwordTextField.editText?.text.toString(),
                        binding.emailTextField.editText?.text.toString() + ConstantesUI.EMAIL_DOMAIN,
                    )
                    viewModel.handleEvent(AddEvent.AddPersona(persona))
                }
                .setNegativeButton(R.string.no) { _, _ ->
                    Toast.makeText(this, ConstantesUI.PERSONA_NO_AGREGADA, Toast.LENGTH_SHORT)
                        .show()
                }
                .setCancelable(false)
                .create()
            dialog.show()
        } else {
            Toast.makeText(this, R.string.empty_fields, Toast.LENGTH_SHORT).show()
        }
    }
}