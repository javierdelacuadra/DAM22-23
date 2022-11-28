package com.example.recyclerview.ui.listequipos

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R
import com.example.recyclerview.domain.modelo.Equipo
import com.example.recyclerview.ui.addequipo.AddEquipoActivity
import com.example.recyclerview.ui.listcomponentes.ListComponentesActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ListEquiposActivity : AppCompatActivity() {

    private lateinit var listaEquipos: RecyclerView

    private val viewModel: ListEquiposViewModel by viewModels()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.addEquipoAction -> {
                loadPantallaAddEquipo()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_equipos)
        viewModel.handleEvent(ListEquiposEvent.GetEquipos)
        listaEquipos = findViewById(R.id.listaEquipos)

        val adapter = AdapterEquipos(ArrayList(),
            object : AdapterEquipos.EquipoActions {
                override fun deleteEquipo(nombreEquipo: String) {
                    borrarEquipo(nombreEquipo)
                }

                override fun mostrarDetalle(equipo: Equipo) {
                    verDetalleEquipo(equipo)
                }
            })

        listaEquipos.adapter = adapter
        listaEquipos.layoutManager = GridLayoutManager(this, 1)

        viewModel.uiState.observe(this) { state ->
            state.lista.let { listaEquipos ->
                adapter.cambiarLista(listaEquipos)
            }
            state.mensaje?.let {
                Timber.i(it)
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun verDetalleEquipo(equipo: Equipo) {
        val intent = Intent(this, ListComponentesActivity::class.java)
        intent.putExtra("nombreEquipo", equipo.nombre)
        startActivity(intent)
    }

    private fun loadPantallaAddEquipo() {
        val intent = Intent(this, AddEquipoActivity::class.java)
        startActivity(intent)
    }

    private fun borrarEquipo(nombreEquipo: String) {
        viewModel.handleEvent(ListEquiposEvent.DeleteEquipos(nombreEquipo))
    }

    public override fun onResume() {
        super.onResume()
        viewModel.handleEvent(ListEquiposEvent.GetEquipos)
    }
}