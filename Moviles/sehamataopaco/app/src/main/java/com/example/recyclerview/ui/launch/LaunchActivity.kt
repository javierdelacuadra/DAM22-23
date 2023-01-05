package com.example.recyclerview.ui.launch

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LaunchActivity : AppCompatActivity() {

    private lateinit var listaDoctores: RecyclerView

    private val viewModel: LaunchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        viewModel.handleEvent(LaunchEvent.GetDoctores)
        listaDoctores = findViewById(R.id.listaDoctores)

        val adapter = AdapterDoctores(ArrayList())

        listaDoctores.adapter = adapter
        listaDoctores.layoutManager = GridLayoutManager(this, 1)

        viewModel.uiState.observe(this) { state ->
            state.lista.let { listaPersonas ->
                adapter.cambiarLista(listaPersonas)
            }
            state.mensaje?.let {
                Timber.i(it)
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}