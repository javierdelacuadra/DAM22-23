package com.example.recyclerview.ui.addequipo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclerview.databinding.AddEquipoBinding
import com.example.recyclerview.domain.modelo.Equipo
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class AddEquipoActivity : AppCompatActivity() {

    private lateinit var binding: AddEquipoBinding
    private val viewModel: AddEquipoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddEquipoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAddEquipo.setOnClickListener {
            addEquipo()
        }

        viewModel.uiState.observe(this) { state ->
            state.mensaje?.let {
                Timber.i(it)
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addEquipo() {
        with(binding) {
            if (editTextNombreEquipo.editText?.text.toString().isEmpty()
                || nacionalidadTextField.editText?.text.toString().isEmpty()
                || editTextPuesto.editText?.text.toString().isEmpty()) {
                Toast.makeText(applicationContext, "Debes rellenar todos los campos", Toast.LENGTH_SHORT)
                Timber.i("Debes rellenar todos los campos")
            } else {
                viewModel.handleEvent(
                    AddEquipoEvent.AddEquipo(
                        Equipo(
                            editTextNombreEquipo.editText?.text.toString(),
                            nacionalidadTextField.editText?.text.toString(),
                            editTextPuesto.editText?.text.toString().toInt()
                        )
                    )
                )
            }
        }
    }
}