package com.example.recyclerview.ui.listequipos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R
import com.example.recyclerview.databinding.EquiposListViewBinding
import com.example.recyclerview.domain.modelo.Equipo

class AdapterEquipos(
    private var equipos: List<Equipo>,
    private val actions: EquipoActions,
) : RecyclerView.Adapter<EquiposViewHolder>() {

    interface EquipoActions {
        fun deleteEquipo(nombreEquipo: String)
        fun mostrarDetalle(equipo: Equipo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquiposViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return EquiposViewHolder(layoutInflater.inflate(R.layout.equipos_list_view, parent, false))
    }

    override fun onBindViewHolder(holder: EquiposViewHolder, position: Int) {
        holder.render(equipos[position], actions)
    }

    override fun getItemCount(): Int = equipos.size

    fun cambiarLista(lista: List<Equipo>) {
        equipos = lista
        notifyDataSetChanged()
    }
}

class EquiposViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private val binding = EquiposListViewBinding.bind(view)

    fun render(
        equipo: Equipo,
        actions: AdapterEquipos.EquipoActions
    ) {

        with(binding) {
            nombreEquipoTextField.text = equipo.nombre
            nacionalidadTextField.text = equipo.nacionalidad
            puestoTextField.text = "Puesto: " + equipo.puesto
            if (equipo.componentes == null) {
                componentesTextField.text = "Componentes: 0"
            } else {
                componentesTextField.text =
                    "Componentes:" + equipo.componentes.size.toString()
            }
        }

        view.findViewById<ImageButton>(R.id.buttonDelete).setOnClickListener {
            actions.deleteEquipo(
                equipo.nombre
            )
        }
        view.findViewById<ImageButton>(R.id.buttonDetalle).setOnClickListener {
            actions.mostrarDetalle(
                Equipo(
                    binding.nombreEquipoTextField.text.toString(),
                    binding.nacionalidadTextField.text.toString(),
                    binding.puestoTextField.text.last().code,
                    equipo.componentes
                )
            )
        }
    }
}