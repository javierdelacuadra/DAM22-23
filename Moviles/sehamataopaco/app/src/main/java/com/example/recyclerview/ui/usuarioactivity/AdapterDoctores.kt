package com.example.recyclerview.ui.usuarioactivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R
import com.example.recyclerview.databinding.DoctoresListViewBinding
import com.example.recyclerview.domain.modelo.Doctor

class AdapterDoctores(
    private var personas: List<Doctor>,
) : RecyclerView.Adapter<DoctorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DoctorViewHolder(layoutInflater.inflate(R.layout.doctores_list_view, parent, false))
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        holder.render(personas[position])
    }

    override fun getItemCount(): Int = personas.size

    fun cambiarLista(lista: List<Doctor>) {
        personas = lista
        notifyDataSetChanged()
    }
}

class DoctorViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val binding = DoctoresListViewBinding.bind(view)

    fun render(
        doctor: Doctor,
    ) {

        with(binding) {
            nombreDoctor.text = doctor.nombre
            especialidadDoctor.text = doctor.especialidad
            emailDoctor.text = doctor.email
            fechaDoctor.text = doctor.fecha.toString()
        }
    }
}