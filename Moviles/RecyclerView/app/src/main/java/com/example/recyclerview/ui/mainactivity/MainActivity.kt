package com.example.recyclerview.ui.mainactivity

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
import com.example.recyclerview.domain.usecases.AddPersonaUseCase
import com.example.recyclerview.ui.common.Constantes
import com.example.recyclerview.ui.listactivity.ListActivity
import com.example.recyclerview.ui.mainactivity.MainViewModel.MainViewModelFactory
import com.example.recyclerview.utils.StringProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            StringProvider.instance(this),
            AddPersonaUseCase(),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
            addButton.setOnClickListener {
                addPersona()
            }
            verListaButton?.setOnClickListener {
                verLista()
            }
            viewModel.uiState.observe(this@MainActivity) { uiState ->
                uiState.persona.let {
                    nameTextField.editText?.setText(it.nombre)
                    passwordTextField.editText?.setText(it.password)
                    emailTextField.editText?.setText(it.email)
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

    private fun verLista() {
        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent)
    }

    private fun addPersona() {
        if (binding.nameTextField.editText?.text.toString().isNotEmpty() &&
            binding.passwordTextField.editText?.text.toString().isNotEmpty() &&
            binding.emailTextField.editText?.text.toString().isNotEmpty()
        ) {
            val dialog = MaterialAlertDialogBuilder(this)
                .setTitle(Constantes.AGREGAR_PERSONA)
                .setMessage(Constantes.DESEA_AGREGAR_UNA_PERSONA)
                .setPositiveButton(R.string.SI) { _, _ ->
                    val persona = Persona(
                        binding.nameTextField.editText?.text.toString(),
                        binding.passwordTextField.editText?.text.toString(),
                        binding.emailTextField.editText?.text.toString() + Constantes.EMAIL_DOMAIN,
                    )
                    AddPersonaUseCase().invoke(persona)
                    Toast.makeText(this, Constantes.PERSONA_AGREGADA, Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton(R.string.no) { _, _ ->
                    Toast.makeText(this, Constantes.PERSONA_NO_AGREGADA, Toast.LENGTH_SHORT).show()
                }
                .setCancelable(false)
                .create()
            dialog.show()
        } else {
            Toast.makeText(this, R.string.empty_fields, Toast.LENGTH_SHORT).show()
        }

    }
}