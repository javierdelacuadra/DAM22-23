package com.example.recyclerview.ui.listactivity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R
import com.example.recyclerview.data.DatabaseRoom
import com.example.recyclerview.data.Repository
import com.example.recyclerview.domain.modelo.Persona
import com.example.recyclerview.domain.usecases.DeletePersonaUseCase
import com.example.recyclerview.domain.usecases.GetPersonasUseCase
import com.example.recyclerview.ui.addactivity.AddActivity
import com.example.recyclerview.ui.common.ConstantesUI
import com.example.recyclerview.ui.updateactivity.UpdateActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import timber.log.Timber

class ListActivity : AppCompatActivity() {

    private lateinit var listaPersonas: RecyclerView

    private val viewModel: ListViewModel by viewModels {
        ListViewModel.ListViewModelFactory(
            GetPersonasUseCase(Repository(DatabaseRoom.getDatabase(this).daoPersonas())),
            DeletePersonaUseCase(Repository(DatabaseRoom.getDatabase(this).daoPersonas()))
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recicler)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            loadPantallaAdd()
        }
        viewModel.handleEvent(ListEvent.GetPersonas)
        listaPersonas = findViewById(R.id.listaPersonas)

        val adapter = AdapterPersonas(ArrayList(),
            object : AdapterPersonas.PersonaActions {
                override fun deletePersona(persona: Persona) {
                    borrarPersona(persona)
                }

                override fun updatePersona(nombre: String, email: String) {
                    actualizarPersona(nombre, email)
                }
            })

        listaPersonas.adapter = adapter
        listaPersonas.layoutManager = GridLayoutManager(this, 1)

        viewModel.uiState.observe(this) { state ->
            state.mensaje.let { error ->
                Timber.e(error)
            }
            state.lista.let { listaPersonas ->
                if (listaPersonas != null) {
                    adapter.cambiarLista(listaPersonas)
                }
            }
        }
    }

    private fun loadPantallaAdd() {
        val intent = Intent(this, AddActivity::class.java)
        startActivity(intent)
    }

    private fun borrarPersona(persona: Persona) {
        viewModel.handleEvent(ListEvent.DeletePersona(persona))
        viewModel.handleEvent(ListEvent.GetPersonas)
    }

    private fun actualizarPersona(nombre: String, email: String) {
        val intent = Intent(this, UpdateActivity::class.java)
        val bundle = Bundle()
        bundle.putString(ConstantesUI.NOMBRE, nombre)
        bundle.putString(ConstantesUI.EMAIL, email)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    public override fun onResume() {
        super.onResume()
        viewModel.handleEvent(ListEvent.GetPersonas)
    }
}