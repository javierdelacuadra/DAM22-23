package com.example.recyclerview.ui.addtarjeta

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclerview.databinding.AddTarjetaBinding
import com.example.recyclerview.domain.modelo.Tarjeta
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTarjetaActivity : AppCompatActivity() {

    private lateinit var binding: AddTarjetaBinding
    private val viewModel: AddTarjetaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddTarjetaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAddTarjeta.setOnClickListener {
           addTarjeta()
        }

        viewModel.uiState.observe(this) { state ->
            state.mensaje?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addTarjeta() {
        if (binding.editTextNumeroTarjeta.editText?.text.toString().isNotEmpty() &&
            binding.editTextFechaCaducidad.editText?.text.toString().isNotEmpty() &&
            binding.editTextCvv.editText?.text.toString().isNotEmpty() &&
            binding.editTextCvv.editText?.text.toString().toIntOrNull() != null
        ) {
            val tarjeta = Tarjeta(
                binding.editTextNumeroTarjeta.editText?.text.toString(),
                binding.editTextFechaCaducidad.editText?.text.toString(),
                binding.editTextCvv.editText?.text.toString().toInt(),
                intent.getStringExtra("email").toString(),
            )
            viewModel.handleEvent(AddTarjetaEvent.AddTarjeta(tarjeta))
        } else {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
        }
    }
    //TODO: poner 2 campos para mes y año y validar fecha correcta y longitud de cvv y numero de tarjeta
}