package com.example.recyclerview.ui.listactivity

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
import com.example.recyclerview.domain.modelo.Persona
import com.example.recyclerview.ui.addactivity.AddActivity
import com.example.recyclerview.ui.common.ConstantesUI
import com.example.recyclerview.ui.updateactivity.UpdateActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ListActivity : AppCompatActivity() {

    private lateinit var listaPersonas: RecyclerView

    private val viewModel: ListViewModel by viewModels()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_action1 -> {
                loadPantallaAddTarjeta()
                true
            }
            R.id.action_action2 -> {
                loadPantallaVerTarjetas()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recicler)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            loadPantallaAddPersona()
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
            state.lista.let { listaPersonas ->
                adapter.cambiarLista(listaPersonas)
            }
            state.mensaje?.let {
                Timber.i(it)
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadPantallaAddPersona() {
        val intent = Intent(this, AddActivity::class.java)
        startActivity(intent)
    }

    private fun loadPantallaAddTarjeta() {

    }

    private fun loadPantallaVerTarjetas() {

    }

    private fun borrarPersona(persona: Persona) {
        viewModel.handleEvent(ListEvent.DeletePersona(persona))
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