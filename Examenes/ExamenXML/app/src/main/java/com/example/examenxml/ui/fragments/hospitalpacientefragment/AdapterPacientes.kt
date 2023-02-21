package com.example.examenxml.ui.fragments.hospitalpacientefragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.examenxml.R
import com.example.examenxml.databinding.ListPacientesBinding
import com.example.examenxml.domain.modelo.Paciente
import com.google.android.material.card.MaterialCardView
import java.util.*

class AdapterPacientes(
    private var pacientes: List<Paciente>,
    private val actions: PacienteActions,
) : RecyclerView.Adapter<PacientesViewHolder>() {

    interface PacienteActions {
        fun verDetalle(hospitalID: UUID)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PacientesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PacientesViewHolder(layoutInflater.inflate(R.layout.list_pacientes, parent, false))
    }

    override fun onBindViewHolder(holder: PacientesViewHolder, position: Int) {
        holder.render(pacientes[position], actions)
    }

    override fun getItemCount(): Int = pacientes.size

    fun cambiarLista(lista: List<Paciente>) {
        pacientes = lista
        notifyDataSetChanged()
    }
}

class PacientesViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ListPacientesBinding.bind(view)

    fun render(
        paciente: Paciente,
        actions: AdapterPacientes.PacienteActions
    ) {

        with(binding) {
            textoNombrePaciente.text = paciente.nombre
        }

        view.findViewById<MaterialCardView>(R.id.pacienteCardView).setOnClickListener {
            val id = paciente.id
            actions.verDetalle(id)
        }
    }
}