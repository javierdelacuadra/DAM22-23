package com.example.recyclerview.ui.doctoractivity.fragments.revisarcitas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R
import com.example.recyclerview.databinding.ListCitasUsuarioBinding
import com.example.recyclerview.domain.modelo.Cita
import com.google.android.material.button.MaterialButton

class AdapterCitasDoctor(
    private var citas: List<Cita>,
    private val actions: CitaActions,
) : RecyclerView.Adapter<CitasViewHolder>() {

    interface CitaActions {
        fun marcarComoRealizada(cita: Cita)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitasViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CitasViewHolder(layoutInflater.inflate(R.layout.list_citas_doctor, parent, false))
    }

    override fun onBindViewHolder(holder: CitasViewHolder, position: Int) {
        holder.render(citas[position], actions)
    }

    override fun getItemCount(): Int = citas.size

    fun cambiarLista(lista: List<Cita>) {
        citas = lista
        notifyDataSetChanged()
    }
}

class CitasViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ListCitasUsuarioBinding.bind(view)

    fun render(
        cita: Cita,
        actions: AdapterCitasDoctor.CitaActions
    ) {

        with(binding) {
            idCita.text = "ID de la cita = " + cita.id.toString()
            fechaCita.text = "Fecha de la cita = " + cita.fecha
            horaCita.text = "Hora de la cita = " + cita.hora
        }

        view.findViewById<MaterialButton>(R.id.btnMarcarCita).setOnClickListener {
            actions.marcarComoRealizada(cita)
        }
    }
}