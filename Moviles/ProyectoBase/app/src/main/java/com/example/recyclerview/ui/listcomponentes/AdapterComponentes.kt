package com.example.recyclerview.ui.listcomponentes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R
import com.example.recyclerview.databinding.ComponenteListViewBinding
import com.example.recyclerview.domain.modelo.Componente

class AdapterComponentes(
    private var listaComponentes: List<Componente>
) : RecyclerView.Adapter<ComponentesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComponentesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ComponentesViewHolder(
            layoutInflater.inflate(
                R.layout.componente_list_view,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ComponentesViewHolder, position: Int) {
        holder.render(listaComponentes[position])
    }

    override fun getItemCount() = listaComponentes.size

    fun cambiarLista(listaComponentes: List<Componente>) {
        this.listaComponentes = listaComponentes
        notifyDataSetChanged()
    }
}

class ComponentesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ComponenteListViewBinding.bind(view)

    fun render(
        componente: Componente,
    ) {

        with(binding) {
            nombreTextField.text = componente.nombre
            nombreEquipoTextField.text = "Equipo: " + componente.nombreEquipo
            if (componente.tipo == 1) {
                tipoTextField.text = "Jugador"
            } else if (componente.tipo == 2) {
                tipoTextField.text = "Entrenador"
            } else {
                tipoTextField.text = "hacker chino?"
            }
        }
    }
}