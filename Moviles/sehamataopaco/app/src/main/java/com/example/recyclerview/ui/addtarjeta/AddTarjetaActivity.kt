package com.example.recyclerview.ui.addtarjeta

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclerview.databinding.AddTarjetaBinding
import com.example.recyclerview.ui.common.ConstantesUI
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

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
                Timber.i(it)
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
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