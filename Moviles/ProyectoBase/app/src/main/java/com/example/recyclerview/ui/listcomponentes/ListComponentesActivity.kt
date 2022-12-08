package com.example.recyclerview.ui.listcomponentes

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ListComponentesActivity : AppCompatActivity() {
    private lateinit var listaComponentes: RecyclerView

    private val viewModel: ListComponentesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_componentes)
        val nombreEquipo = intent.getStringExtra("nombreEquipo")!!
        viewModel.handleEvent(ListComponentesEvent.GetComponentes(nombreEquipo))
        listaComponentes = findViewById(R.id.listaComponentes)

        val adapter = AdapterComponentes(ArrayList())

        listaComponentes.adapter = adapter
        listaComponentes.layoutManager = GridLayoutManager(this, 1)

        viewModel.uiState.observe(this) { state ->
            state.mensaje.let { error ->
                Timber.e(error)
            }
            state.lista.let { listaComponentes ->
                adapter.cambiarLista(listaComponentes)
            }
        }
    }
}