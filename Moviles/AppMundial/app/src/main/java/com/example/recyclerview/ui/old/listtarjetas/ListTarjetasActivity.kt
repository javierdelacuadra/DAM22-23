package com.example.recyclerview.ui.old.listtarjetas

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ListTarjetasActivity : AppCompatActivity() {
    private lateinit var listaTarjetas: RecyclerView

    private val viewModel: ListTarjetaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_tarjetas)
        viewModel.handleEvent(ListTarjetaEvent.GetTarjetas)
        listaTarjetas = findViewById(R.id.listaTarjetas)

        val adapter = AdapterTarjetas(ArrayList())

        listaTarjetas.adapter = adapter
        listaTarjetas.layoutManager = androidx.recyclerview.widget.GridLayoutManager(this, 1)

        viewModel.uiState.observe(this) { state ->
            state.mensaje.let { error ->
                Timber.e(error)
            }
            state.lista.let { listaTarjetas ->
                if (listaTarjetas != null) {
                    adapter.cambiarLista(listaTarjetas)
                }
            }
        }
    }

    public override fun onResume() {
        super.onResume()
        viewModel.handleEvent(ListTarjetaEvent.GetTarjetas)
    }
}