package com.example.examenxml.ui.fragments.detallefragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.examenxml.R
import com.example.examenxml.databinding.ListEnfermedadesBinding
import com.example.examenxml.domain.modelo.Enfermedad

class AdapterEnfermedades(
    private var enfermedades: List<Enfermedad>
) : RecyclerView.Adapter<EnfermedadViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EnfermedadViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return EnfermedadViewHolder(
            layoutInflater.inflate(
                R.layout.list_enfermedades,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EnfermedadViewHolder, position: Int) {
        holder.render(enfermedades[position])
    }

    override fun getItemCount(): Int = enfermedades.size

    fun cambiarLista(lista: List<Enfermedad>) {
        enfermedades = lista
        notifyDataSetChanged()
    }
}

class EnfermedadViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ListEnfermedadesBinding.bind(view)

    fun render(
        enfermedad: Enfermedad,
    ) {

        with(binding) {
            textoNombreEnfermedad.text = enfermedad.nombre
        }
    }
}