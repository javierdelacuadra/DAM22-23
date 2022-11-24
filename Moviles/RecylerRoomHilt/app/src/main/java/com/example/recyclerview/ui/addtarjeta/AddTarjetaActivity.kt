package com.example.recyclerview.ui.addtarjeta

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclerview.databinding.AddTarjetaBinding
import com.example.recyclerview.ui.common.ConstantesUI
import com.example.recyclerview.ui.listtarjetas.ListTarjetasActivity
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
        binding.buttonVerListaTarjetas.setOnClickListener {
            verListaTarjetas()
        }

        viewModel.uiState.observe(this) { state ->
            state.mensaje?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun verListaTarjetas() {
        val intent = Intent(this, ListTarjetasActivity::class.java)
        startActivity(intent)
    }

    private fun addTarjeta() {
        val numeroTarjeta = binding.editTextNumeroTarjeta.editText?.text.toString()
        val mes = binding.editTextMes.editText?.text.toString()
        val year = binding.editTextYear.editText?.text.toString()
        val cvv = binding.editTextCvv.editText?.text.toString()
        val email = intent.getStringExtra(ConstantesUI.EMAIL)
        viewModel.handleEvent(
            AddTarjetaEvent.AddTarjeta(
                numeroTarjeta,
                mes,
                year,
                cvv,
                email ?: ConstantesUI.NADA
            )
        )
    }
}