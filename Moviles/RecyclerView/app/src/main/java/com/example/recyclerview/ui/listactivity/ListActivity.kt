package com.example.recyclerview.ui.listactivity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R
import com.example.recyclerview.domain.usecases.DeletePersonaUseCase
import com.example.recyclerview.domain.usecases.GetPersonasUseCase
import com.example.recyclerview.ui.common.Constantes
import com.example.recyclerview.ui.updateactivity.UpdateActivity
import timber.log.Timber

class ListActivity : AppCompatActivity() {

    private lateinit var listaPersonas: RecyclerView

    private val viewModel: ListViewModel by viewModels {
        ListViewModel.ListViewModelFactory(
            GetPersonasUseCase(),
            DeletePersonaUseCase(),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recicler)
        viewModel.cargarPersonas()
        listaPersonas = findViewById(R.id.listaPersonas)
        val adapter = AdapterPersonas(ArrayList(), ::deletePersona, ::updatePersona)

        listaPersonas.adapter = adapter
        listaPersonas.layoutManager = GridLayoutManager(this, 1)

        viewModel.uiState.observe(this) { state ->
            state.error.let { error ->
                Timber.e(error)
            }
            state.lista.let { listaPersonas ->
                if (listaPersonas != null) {
                    adapter.cambiarLista(listaPersonas)
                }
            }
        }
    }

    private fun deletePersona(email: String) {
        viewModel.deletePersona(email)
    }

    private fun updatePersona(nombre: String, email: String) {
        val intent = Intent(this, UpdateActivity::class.java)
        intent.putExtra(Constantes.NOMBRE, nombre)
        intent.putExtra(Constantes.EMAIL, email)
        startActivity(intent)
    }

    public override fun onResume() {
        super.onResume()
        viewModel.cargarPersonas()
    }
}